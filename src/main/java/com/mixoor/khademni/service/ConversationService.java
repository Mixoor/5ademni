package com.mixoor.khademni.service;


import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.model.Conversation;
import com.mixoor.khademni.payload.request.ConversationRequest;
import com.mixoor.khademni.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {



    @Autowired
    ConversationRepository conversationRepository;





}
