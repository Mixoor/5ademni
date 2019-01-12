package com.mixoor.khademni.repository;


import com.mixoor.khademni.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.receiver.id= :id order by ?#{#pageable}")
    Page<Notification> getAllNotificationByReceiver(@Param("id") Long receiver, Pageable pageable);

    @Query("update Notification n set n.isRead=1 where n.receiver.id=:id")
    void updateNotification(@Param("id") Long id);

}
