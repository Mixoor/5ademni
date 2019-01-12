package com.mixoor.khademni.controller.ws;


import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.payload.request.MessageRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class chatController {

    @Autowired
    MessageService messageService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public ResponseEntity sendMessage(UserPrincipal userPrincipal , MessageRequest request){
        messageService.sendMessage(userPrincipal,request);
        //send to receiver

        simpMessagingTemplate.convertAndSendToUser(userPrincipal.getName(),"/chat",request);

        return  ResponseEntity.ok().body(new ApiResponse(true,"Message Send succefully"));

    }

}
