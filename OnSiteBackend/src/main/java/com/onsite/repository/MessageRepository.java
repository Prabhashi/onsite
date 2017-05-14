package com.onsite.repository;

import com.onsite.model.Message;
import com.onsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TJR on 3/9/2017.
 */
public interface MessageRepository extends JpaRepository<Message, Integer>{

   List<Message> findByReciever(User user);

   List<Message> findBySender(User sender);
}
