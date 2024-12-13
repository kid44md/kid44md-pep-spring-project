package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    
    MessageRepository  messageRepository;

    @Autowired
    public MessageService(MessageRepository  messageRepository){
    this.messageRepository =  messageRepository;
    }


    public Message createMessage (Message messages){
        Optional<Message> messageOptional = messageRepository.findByPostedBy(messages.getPostedBy());
        if(messageOptional.isPresent()){
          return messageRepository.save(messages);
        }
        return null; 
    }

    public List<Message> getAllMessages(){

    return messageRepository.findAll();
    }

    public Message getMessageById (int id){
      return messageRepository.findById(id);
    }


    public int deleteMessageByPostedId(int message_id) {
    int rowAffected = messageRepository.deleteById(message_id);   
      return rowAffected;
    }

    @Transactional
    public int updateMessageById(int message_id, String message) {
      Message messageOptional = messageRepository.findById(message_id);
      if(messageOptional != null){
      int rowAffected = messageRepository.updateMessageById(message_id,message);
      return rowAffected;
      }
        return 0;
      }
    public List<Message>getAllMessagesByAccountId(int accountId){
      
      return messageRepository.findAllById(accountId);
    }
}
