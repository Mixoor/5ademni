package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.exception.UnauthorizedException;
import com.mixoor.khademni.model.Conversation;
import com.mixoor.khademni.model.Document;
import com.mixoor.khademni.model.Message;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.request.ConversationRequest;
import com.mixoor.khademni.payload.request.MessageRequest;
import com.mixoor.khademni.payload.response.MessageResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.repository.ConversationRepository;
import com.mixoor.khademni.repository.MessageRepository;
import com.mixoor.khademni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {


    @Autowired
    DocumentStorageService documentStorageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    MessageRepository messageRepository;


    public Message sendMessage(UserPrincipal userPrincipal, MessageRequest request) {

        User sender = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BadRequestException("user doesn't Exists"));

        User receiver = userRepository.findById(request.getReceiver())
                .orElseThrow(() -> new BadRequestException("user doesn't Exists"));

        Conversation conversation = conversationRepository.findById(request.getConversation())
                .orElseThrow(() -> new BadRequestException("Conversation doesn't exist"));

        //check if message has attachment
        Document document = null;
        if (!request.getFile().isEmpty()) {
            document = documentStorageService.documentToMessage(
                    documentStorageService.storeFile(request.getFile())
                    , sender
            );


        }

        Message message = new Message(request.getContent(), 0, sender, receiver, document, conversation);
        messageRepository.save(message);

        return message;

    }


    public PagedResponse<MessageResponse> getAllMessagesInConversation(UserPrincipal userPrincipal, Long conversationID, int page, int size) {


        Conversation conversation = conversationRepository.findById(conversationID)
                .orElseThrow(() -> new BadRequestException("Conversation doesn't exist"));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");




        Page<Message> messages = messageRepository.findByConversation(conversation, pageable);

        if (!(conversation.getUser1().getId().equals(userPrincipal.getId()) || conversation.getUser2().getId().equals(userPrincipal.getId())))
            throw new UnauthorizedException("Unauthorized request");

        if (messages.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), page, size, messages.getTotalElements(), messages.getTotalPages(), messages.isLast());


        List<MessageResponse> messageResponses = messages.stream().map(message ->
                ModelMapper.mapMessageToResponse(message))
                .collect(Collectors.toList());

        return new PagedResponse<MessageResponse>(messageResponses, page, size, messages.getTotalElements(),
                messages.getTotalPages(), messages.isLast());


    }

    public void updateMesssageStatus(UserPrincipal principal) {


        //
        List<Message> getAll = messageRepository
                .getAllMessageByReceiverAndNotReadable(principal.getId(), 0).stream().map(message -> {
                    message.setStatus(1);
                    return message;

                }).collect(Collectors.toList());

        getAll.forEach(message -> {
            message.setStatus(1);
            messageRepository.save(message);
        });


    }


}
