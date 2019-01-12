package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Conversation;
import com.mixoor.khademni.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByConversation(Conversation conversation, Pageable pageable);

    @Query(value = "select m from Message m inner join " +
            "(select max(Message.id) as id from Message where Message.conversation.id in :ids group by Messge.conversationd) AS t" +
            "on m.id = t.id order by ?#{#pageable}", nativeQuery = true)
    Page<Message> getLastMessageByConversation(@Param("ids") List<Long> conversations, Pageable pageable);

    @Query("select m from Message m where m.receiver.id =:id order by m.id DESC ")
    List<Message> getAllMessageByReceiver(@Param("id") Long id);


    @Query("select m from Message m where m.receiver.id =:id  and m.status=:status order by m.id DESC ")
    List<Message> getAllMessageByReceiverAndNotReadable(@Param("id") Long id, @Param("status") int status);

}
