package com.onsite.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsite.globals.Const;
import com.onsite.model.ResponseState;
import com.onsite.model.User;
import com.onsite.repository.UserRepository;
import com.onsite.tools.ImageDecoder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TJR on 12/20/2016.
 */
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = "application/json")
    public Map<String, String> userLogin(@RequestBody User u) {

        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByUsername(u.getUsername());
        if (user == null) {
            response.put("state", "failed");
            return response;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
////user input vs stored password
        if (encoder.matches(u.getPassword(), user.getPassword())) {
            response.put("state", user.getId().toString());
            response.put("role", user.getRole());
        } else {
            response.put("state", "failed");
        }
        return response;

    }

    /**
     * Checks if user exists in the database
     *
     * @param username username to be tested
     * @return availability as json
     */
    @RequestMapping(value = "/user/username_available/{username}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseState checkUserAvailability(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user != null)
            return new ResponseState("not_available");
        else
            return new ResponseState("available");

    }


    @RequestMapping(value = "/user/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/user/find/{role}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getByRole(@PathVariable String role) {
        return userRepository.findByRole(role);
    }


    @RequestMapping(value = "/user/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseState registerUser(@RequestBody User u
    ) {

        u.setRole(u.getRole().toLowerCase());
        u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
        User identical = userRepository.findByUsername(u.getUsername());
        User identicalEmail = userRepository.findByEmail(u.getEmail());
        u.setProfilePicUrl("http://gurucul.com/wp-content/uploads/2015/01/default-user-icon-profile.png");

        if (identical != null) return new ResponseState("username_exists");
        if (identicalEmail != null) return new ResponseState("email_exists");

        userRepository.save(u);
        return new ResponseState("" + u.getId());

    }

    @RequestMapping(value = "/user/edit",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseState editUser(@RequestBody User u
    ) {

        User existing = userRepository.findOne(u.getId());
        if (existing == null)
            return new ResponseState("failed");

        if (u.getUsername() != null) {
            //find if username exists

            User identical = userRepository.findByUsername(u.getUsername());

            if (identical == null || identical.getId() == u.getId())
                existing.setUsername(u.getUsername());

        }
        if (u.getEmail() != null)
            existing.setEmail(u.getEmail());

        if (u.getPhoneNumber() != null)
            existing.setPhoneNumber(u.getPhoneNumber());

        if (u.getProfilePicUrl() != null) {
            String imageAsString = u.getProfilePicUrl();
            ImageDecoder imageDecoder = new ImageDecoder();
            String fileName = imageDecoder.decodeAndSave("profile", imageAsString);
            existing.setProfilePicUrl("http://localhost:8080/images/get/" + fileName);
        }

        userRepository.save(existing);
        return new ResponseState("" + u.getId());

    }


    /**
     * Update user profile data
     */
    @RequestMapping(value = "/user/update",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseState updateUser(@RequestBody User user) {


        if (user.getUsername() != null)
            user.setUsername(user.getUsername());

        if (user.getFullName() != null)
            user.setFullName(user.getFullName());

        if (user.getPhoneNumber() != null)
            user.setPhoneNumber(user.getPhoneNumber());

        if (user.getPassword() != null)
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        //TODO : Update other attributes
        userRepository.save(user);
        return new ResponseState("success");
    }

    @RequestMapping(value = "/user/id/{id}")
    public User getUserById(@PathVariable Integer id) {
        User u = userRepository.findOne(id);
        return u;
    }

    @RequestMapping(value = "/user/all/{role}")
    public List<User> getUserByRole(@PathVariable String role) {
        List<User> matches = userRepository.findByRole(role);
        return matches;
    }

    @RequestMapping(value = "/user/like/{string}")
    public List<User> getUserById(@PathVariable String string) {
        List<User> matches = userRepository.findTop5ByFullNameContaining(string);
        return matches;
    }

    //TODO send email

    private void saveProfilePicture(MultipartFile profilePic, User user) {
        byte[] bytes = new byte[0];
        try {
            bytes = profilePic.getBytes();


            File dir = new File("ProfilePictures");
            if (!dir.exists())
                dir.mkdirs();

            // Create the file on server


            File serverFile = new File(dir.getAbsolutePath() + File.separator + user.getUsername() + "_profile.jpg");
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            user.setProfilePicUrl(Const.HOST_URL + "image/get/profile/" + serverFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
