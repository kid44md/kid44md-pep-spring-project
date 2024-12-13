package com.example.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
//need to change the request mapping to the right api base in the lab, this is the class level 
//could place this at the method level to further specify the URL Pattern
@RequestMapping("/")
public class SocialMediaController {
@Autowired
AccountService accountService;
@Autowired
MessageService messageService; 
public SocialMediaController(){
}

    //POST localhost:8080/register.
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        if (account.getUsername().isEmpty() || account.getUsername().length() < 4 ){
             return ResponseEntity.status(400).build();
        }
        Account acct = accountService.saveNewAccount(account);
        if (acct != null){
            return ResponseEntity.status(200).body(acct);
        }else{
            return ResponseEntity.status(409).build();
        }
    }

    //The registration will be successful if and only if the username 
    //is not blank, the password is at least 4 characters long, and an Account 
    //with that username does not already exist. If all these conditions are met, 
    //the response body should contain a JSON of the Account, including its account_id. 
    //The response status should be 200 OK, which is the default. The new account should be persisted to the database.
//If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
//If the registration is not successful for some other reason, the response status should be 400. (Client error)


@PostMapping("login")
public ResponseEntity<Account> login(@RequestBody Account account){
Account acct = accountService.login(account); 
if ( acct != null){
    return ResponseEntity.status(200).body(acct);
}else{
    return ResponseEntity.status(401).build();
    }
}



// As a user, I should be able to verify my login on the endpoint POST localhost:8080/login. 
// The request body will contain a JSON representation of an Account, not containing an account_id. 
// In the future, this action may generate a Session token to allow the user to securely use the site. 
// We will not worry about this for now.

// The login will be successful if and only if the username and password provided in the 
// request body JSON match a real account existing on the database.
//  If successful, the response body should contain a JSON of the account in the response body, 
//  including its account_id. The response status should be 200 OK, which is the default.

// If the login is not successful, the response status should be 401. (Unauthorized)




@PostMapping("messages")
public ResponseEntity<Message> createMessages(@RequestBody Message messages){
    System.out.println(messages);
    System.out.println("Message Body above!");
    if(messages.getMessageText().isEmpty() || messages.getMessageText().length() > 255){
        return ResponseEntity.status(400).build();
        }

      Message message =  messageService.createMessage(messages);
      if(message != null){
        return ResponseEntity.status(200).body(message); 
      }else{
        return ResponseEntity.status(400).build();
      }

}
// As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages. 
// The request body will contain a JSON representation of a message, which should be persisted to the database, 
// but will not contain a message_id.

// The creation of the message will be successful if and only if the message_text is not blank,
//  is under 255 characters, and posted_by refers to a real, existing user. 
//  If successful, the response body should contain a JSON of the message, including its message_id. 
//  The response status should be 200, which is the default. The new message should be persisted to the database.

// If the creation of the message is not successful, the response status should be 400. (Client error)




@GetMapping("messages")
public ResponseEntity<List<Message>> getMessages(){
List<Message> message = messageService.getAllMessages();
System.out.println(message);
return ResponseEntity.status(200).body(message);
}

// Get All Messages
// As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages.
// The response body should contain a JSON representation of a list containing all messages retrieved from the database. 
// It is expected for the list to simply be empty if there are no messages. 
// The response status should always be 200, which is the default.




@GetMapping("messages/{message_id}")
public ResponseEntity<Message> getMessageById(@PathVariable int message_id){
    Message message = messageService.getMessageById(message_id);
    return ResponseEntity.status(200).body(message);
}
//Get One Message Given Message Id
//As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.

//The response body should contain a JSON representation of the message identified by the message_id. 
//It is expected for the response body to simply be empty if there is no such message.
 //The response status should always be 200, which is the default.





@DeleteMapping("messages/{message_id}")
public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id){
    int rowAffected = messageService.deleteMessageByPostedId(message_id);
    if(rowAffected == 1){
        return ResponseEntity.status(200).body(rowAffected);
    }else{
        return ResponseEntity.status(200).build();
    }
    
    
}
 //Delete a Message Given Message Id
 //As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.
 
//  The deletion of an existing message should remove an existing message from the database. 
//  If the message existed, the response body should contain the number of rows updated (1). 
//  The response status should be 200, which is the default.
//  If the message did not exist, the response status should be 200, but the response body should be empty. 
//  This is because the DELETE verb is intended to be idempotent, ie, multiple calls to the DELETE endpoint 
//  should respond with the same type of response.









@PatchMapping("messages/{message_id}")
public ResponseEntity<Integer> updateMessageById(@PathVariable int message_id, @RequestBody Message message){
    if(message.getMessageText().isBlank() || message.getMessageText().length() > 255){
    return ResponseEntity.status(400).build();
    }
    int rowAffected = messageService.updateMessageById(message_id, message.getMessageText());
    System.out.println(rowAffected);
    System.out.println("RESULT");
    if(rowAffected == 1){
        return ResponseEntity.status(200).body(rowAffected);
    }else{
        return ResponseEntity.status(400).build();
    }
}

// Update Message Given Message Id
// As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}. 
// The request body should contain a new message_text values to replace the message identified by message_id. 
// The request body can not be guaranteed to contain any other information.

// The update of a message should be successful if and only if the message id already exists and the new message_text is not blank
//  and is not over 255 characters. If the update is successful, the response body should contain the number of rows updated (1), 
//  and the response status should be 200, which is the default. 
//  The message existing on the database should have the updated message_text.
// If the update of the message is not successful for any reason, the response status should be 400. (Client error)
 



@GetMapping("accounts/{account_id}/messages")
public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable int account_id){
List<Message> message = messageService.getAllMessagesByAccountId(account_id);
return ResponseEntity.status(200).body(message);
}

// As a user, I should be able to submit a GET request on the endpoint 
//GET localhost:8080/accounts/{account_id}/messages.
// The response body should contain a JSON representation of a list containing all messages posted by a particular user, 
// which is retrieved from the database. It is expected for the list to simply be empty if there are no messages. 
// The response status should always be 200, which is the default


}
