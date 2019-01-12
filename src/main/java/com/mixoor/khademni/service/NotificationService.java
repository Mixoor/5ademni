package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.Notification;
import com.mixoor.khademni.model.NotificationType;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.request.NotificationRequest;
import com.mixoor.khademni.payload.response.NotificationResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.repository.NotificationRepository;
import com.mixoor.khademni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void createNotification(UserPrincipal userPrincipal, NotificationRequest request) {

        User sender = userRepository.findById(request.getReceiver()).orElseThrow(
                () -> new BadRequestException("User Invalid "));

        User receiver = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new BadRequestException("User Invalid"));


        // Fill and save  Notification
        Notification notification = new Notification(request.getMessage(), receiver, sender, NotificationType.values()[request.getType()], request.getUrl(), 0);
        notificationRepository.save(notification);

        // Send Response to user by the websocket
        NotificationResponse response = ModelMapper.mapResponseFromNotification(notification);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(notification.getReceiver().getId()), "/notification", response);


    }

    public PagedResponse<NotificationResponse> getNotifications(UserPrincipal userPrincipal, int size, int page) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Notification> notifications = notificationRepository.getAllNotificationByReceiver(userPrincipal.getId(), pageable);

        if (notifications.isEmpty())
            return new PagedResponse<>(Collections.emptyList(), page, size, notifications.getTotalElements(), notifications.getTotalPages(), notifications.isLast());


        List<NotificationResponse> response = notifications.stream().map(notification ->
                ModelMapper.mapResponseFromNotification(notification)
        ).collect(Collectors.toList());

        return new PagedResponse<NotificationResponse>(response, notifications.getNumber(), notifications.getSize(), notifications.getTotalElements(), notifications.getTotalPages(), notifications.isLast());


    }

    public void updateNotification(UserPrincipal receiver) {
        notificationRepository.updateNotification(receiver.getId());
    }


}

