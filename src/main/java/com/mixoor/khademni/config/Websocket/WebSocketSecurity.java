package com.mixoor.khademni.config.Websocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.*;

@Configuration
public class WebSocketSecurity extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

    messages.simpTypeMatchers(CONNECT,UNSUBSCRIBE,DISCONNECT,HEARTBEAT).permitAll()

            .simpDestMatchers("/app/**","/chat/**","/notification/**","/user/**").authenticated()
            .simpSubscribeDestMatchers("/chat","/notification","/user").authenticated().anyMessage().denyAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
