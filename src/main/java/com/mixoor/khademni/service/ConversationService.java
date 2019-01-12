package com.mixoor.khademni.service;


import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.exception.UnauthorizedException;
import com.mixoor.khademni.model.Conversation;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.request.ConversationRequest;
import com.mixoor.khademni.repository.ConversationRepository;
import com.mixoor.khademni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    ConversationRepository conversationRepository;

    public Conversation createConversation(UserPrincipal userPrincipal, ConversationRequest request) {

        if (!(userPrincipal.getId().equals(request.getUser1()) || userPrincipal.getId().equals(request.getUser2())))
            throw new UnauthorizedException("Request Unauthorized");

        // Check if user in the conversation exist ?
        User user1 = userRepository.findById(request.getUser1()).orElseThrow(() -> new BadRequestException("User Invalid "));
        User user2 = userRepository.findById(request.getUser2()).orElseThrow(() -> new BadRequestException("User Invalid "));

        //check if conversation Exist else w
        Conversation c = conversationRepository.getConversationByUser1AndUser2(user1.getId(), user2.getId())
                .orElseGet(() -> {
                    Conversation conversation = new Conversation(user1, user2, 1);
                    conversationRepository.save(conversation);
                    return conversation;
                });






        //TODO Status value in conversation is need it  ?

        return c;

    }


}
