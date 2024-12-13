package com.example.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

@Repository
public interface MessageRepository  extends JpaRepository <Message, Integer> {

    
   Optional<Message> findByPostedBy(Integer postedBy);
   
    List<Message> findAll();

    Message findById(int id);

    @Modifying
    @Query("delete from Message where messageId = ?1")
    int deleteById(int id);

// @Modifying
// @Transactional
// @Query("UPDATE Message m SET m.messageText=:message where m.messageId=:id")
// int updateMessageById(@Param("id")int id, @Param("message")String message);

@Modifying
@Query("UPDATE Message m SET m.messageText=?2 where m.messageId=?1")
int updateMessageById(int id, String message);


@Query("FROM Message where postedBy=?1")
List<Message> findAllById(int id);

}
