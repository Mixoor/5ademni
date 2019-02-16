package com.mixoor.khademni.config.Websocket;

import com.mixoor.khademni.config.CustomUserDetailsService;
import com.mixoor.khademni.config.JwtTokenProvider;
import com.mixoor.khademni.config.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

import java.security.Principal;
import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private DefaultSimpUserRegistry userRegistry = new DefaultSimpUserRegistry();

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/secured-ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chat", "/notification");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String token = accessor.getFirstNativeHeader("Token");
                    System.out.println("Token : " + token);

                    try {


                        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                            Long userId = jwtTokenProvider.getUserIdfromJWT(token);

                            UserDetails userDetails = customUserDetailsService.loadUserById(userId);

                            // If the user is not valid than return an empty message

                            if (Objects.isNull(userDetails))
                                return null;



                            Principal authentication =null;
                            authentication = new UsernamePasswordAuthenticationToken(userDetails,
                                    null, userDetails.getAuthorities());

                            UserPrincipal  userPrincipal = (UserPrincipal) userDetails;
                            accessor.setUser(authentication);


                            accessor.setLeaveMutable(true);


                        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                            if (Objects.nonNull(authentication))
                                System.out.println("Disconnected Auth : " + authentication.getName());
                            else
                                System.out.println("Disconnected Sess : " + accessor.getSessionId());
                        }


                    } catch (Exception e) {
                        System.out.print("Could not set user authentication in security context " + e);
                    }


                }

                //TODO Experimental
                return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());

            }
        });
    }










}
