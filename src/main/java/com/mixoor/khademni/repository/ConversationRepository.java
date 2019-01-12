package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Conversation;
import com.mixoor.khademni.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Page<Conversation> findByUser1OrUser2(User user1, User user2, Pageable pageable);

    Page<Conversation> findByUser1(User user1, Pageable pageable);

    Page<Conversation> findByUser2(User user2, Pageable pageable);


    @Query("select c from Conversation c where c.user2.id=:id or c.user1.id=:id order by ?#{#pageable}")
    Page<Conversation> getAllConversationByUser(@Param("id") Long id, Pageable pageable);

    @Query("select c from Conversation c where (c.user2.id=:user2 and c.user1.id=:user1) or (c.user2.id=:user1 and c.user1.id=:user2)")
    Optional<Conversation> getConversationByUser1AndUser2(@Param("user1") Long user1, @Param("user2") Long user2);


}
