package com.mixoor.khademni.controller;


import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.MessageResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {


    @Autowired
    MessageService messageService;



    @GetMapping("/api/message/{id}")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<MessageResponse> getAllMessageInConversation(UserPrincipal userPrincipal, @PathVariable(value = "id") Long id,
                                                                      @RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                      @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
        return messageService
                .getAllMessagesInConversation(userPrincipal,id,page,size);


    }

    @PutMapping("/api/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> UpdateMessages(UserPrincipal userPrincipal){

        messageService.updateMesssageStatus(userPrincipal);
        return ResponseEntity.ok().body(new ApiResponse(true,"Messages Updates successfully"));
    }

}

