package com.onsite.controllers;

import com.onsite.model.Message;
import com.onsite.model.ResponseState;
import com.onsite.model.User;
import com.onsite.repository.MessageRepository;
import com.onsite.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TJR on 3/9/2017.
 */
@RestController
public class MessageController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(
            value = "/message/send",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    public ResponseState sendMessage(@RequestBody String jsonBody){
        JSONObject messageJson = new JSONObject(jsonBody);
        Message m = new Message();
        Integer senderId = messageJson.getInt("senderId");
        String receiverUsername = messageJson.getString("recieverUsername");
        String messageBody = messageJson.getString("messageBody");

        User receiver = userRepository.findByUsername(receiverUsername);
        User sender = userRepository.findOne(senderId);

        if(receiver != null && sender != null){
            m.setSender(sender);
            m.setReciever(receiver);
            m.setMessageBody(messageBody);
            m.setSentTime(new Date());
            messageRepository.save(m);
            return new ResponseState("success");
        }
        else return new ResponseState("failed");

    }



    @RequestMapping(
            value = "/message/get/{senderId}/{receiverUsername}",
            method = RequestMethod.GET,
            produces = "application/json",
            consumes = "application/json"
    )
    public List<Message> getMessages(
            @PathVariable Integer senderId,
            @PathVariable String receiverUsername
    ){

        User receiver = userRepository.findByUsername(receiverUsername);
        User sender = userRepository.findOne(senderId);


        if(receiver != null && sender != null){
            List<Message> retMsg = new ArrayList<>();
            List<Message> messages = messageRepository.findByReciever(sender);
            for(Message msg : messages){
                if(msg.getSender().getId() == receiver.getId())
                    retMsg.add(msg);
            }
            messages = messageRepository.findBySender(receiver);
            for(Message msg : messages){
                if(msg.getReciever().getId() == sender.getId())
                    retMsg.add(msg);
            }

            return retMsg;

        }
        else return null;

    }



    @RequestMapping(
         value="/message/delete",
          method=RequestMethod.POST,
          produces="applcation/json",
          consumes="application/json"

    )
    public ResponseState deleteMessage(@RequestBody String jsonBody){
        JSONObject deleteJson=new JSONObject(jsonBody);
        Integer messageId=deleteJson.getInt("messageId");

        Message message=messageRepository.findOne(messageId);
        return new ResponseState("success");

    }



    @RequestMapping(
            value = "/message/inbox/{userId}",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public List<Message> getInbox(@PathVariable Integer userId) {

          User user=userRepository.findOne(userId);
          List<Message> message=messageRepository.findByReciever(user);
          return message;
}

}
