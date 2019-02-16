package com.mixoor.khademni.controller;


import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.payload.response.NotificationResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;



    @GetMapping("/")
    public PagedResponse<NotificationResponse> getAll(@CurrentUser UserPrincipal userPrincipal,
                                                      @RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page ,
                                                      @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int size
                                                      ){
        return  notificationService.getNotifications(userPrincipal,size,page);
    }


}
