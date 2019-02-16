//package com.mixoor.khademni.controller.ws;
//
//
//import com.mixoor.khademni.config.CurrentUser;
//import com.mixoor.khademni.config.UserPrincipal;
//import com.mixoor.khademni.payload.request.MessageRequest;
//import com.mixoor.khademni.service.MessageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class chatController {
////
////    @Autowired
////    MessageService messageService;
////
////    @Autowired
////    SimpMessagingTemplate simpMessagingTemplate;
////
////    @MessageMapping("/chat")
////    public ResponseEntity sendMessage(@CurrentUser UserPrincipal principal, MessageRequest message){
////
////         //com.mixoor.khademni.model.Message m = messageService.sendMessage(principal,message);
////         //MessageResponse messageResponse= ModelMapper.mapMessageToResponse(m);
////
////             simpMessagingTemplate.convertAndSendToUser(principal.getEmail(),"/chat/message",message);
////
////        return  ResponseEntity.ok().body("Message Send successful");
////
////    }
//
//}
