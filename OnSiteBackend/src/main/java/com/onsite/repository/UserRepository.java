package com.onsite.repository;


import com.onsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by TJR on 12/20/2016.
 */

public interface UserRepository extends JpaRepository<User,Integer> {
     User findByUsername(String username);
     User findById(Integer id);
     User findByUsernameAndPassword(String username, String password);
     List<User> findTop5ByFullNameContaining(String partialString);

    User findByEmail(String email);

    List<User> findByRole(String role);
}
