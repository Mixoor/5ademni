package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Conversation;
import com.mixoor.khademni.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Page<Conversation> findByUser1OrUser2(User user1, User user2, Pageable pageable);

    Page<Conversation> findByUser1(User user1, Pageable pageable);

    Page<Conversation> findByUser2(User user2, Pageable pageable);

}
