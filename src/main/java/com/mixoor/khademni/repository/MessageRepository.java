package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Conversation;
import com.mixoor.khademni.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByConversation(Conversation conversation, Pageable pageable);
}
