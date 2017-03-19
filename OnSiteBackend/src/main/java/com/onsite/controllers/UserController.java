package com.onsite.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsite.globals.Const;
import com.onsite.model.ResponseState;
import com.onsite.model.User;
import com.onsite.repository.UserRepository;
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


    /**
     * Login authentication
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = "application/json")
    public Map<String, String> userLogin(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "password") String password) {

        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByUsername(username);
        if (user == null) response.put("state", "failed");


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        if (encoder.matches(password, user.getPassword())) {
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


    /**
     * Registers user, returns if the user has been registered successfully
     *
     * @return success state
     */
    @RequestMapping(value = "/user/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseState registerUser(@RequestParam(value = "fullName", required = false) String fullName,
                                      @RequestParam(value = "username", required = false) String username,
                                      @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "deviceId") String deviceId,
                                      @RequestParam(value = "email", required = false) String email,
                                      @RequestParam(value = "role") String role
    ) {
        User u = new User();
        u.setFullName(fullName);
        u.setPassword(password);
        u.setUsername(username);
        u.setPhoneNumber(phoneNumber);
        u.setDeviceId(deviceId);
        u.setEmail(email);
        u.setRole(role);
        u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
        User identical = userRepository.findByUsername(u.getUsername());
        User identicalEmail = userRepository.findByEmail(u.getEmail());
        u.setProfilePicUrl("http://gurucul.com/wp-content/uploads/2015/01/default-user-icon-profile.png");

        if (identical == null && identicalEmail == null) {
            userRepository.save(u);
            return new ResponseState("" + u.getId());
        } else if( identical == null){
            return new ResponseState("username_exists");
        }else{
            return  new ResponseState("email_exists");
        }

    }


    /**
     * Update user profile data
     */
    @RequestMapping(value = "/user/update",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseState updateUser(

            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "id") Integer userId,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture
    ) {

        User user = userRepository.findOne(userId);
        if (username != null)
            user.setUsername(username);

        if (fullName != null)
            user.setFullName(fullName);

        if (phoneNumber != null)
            user.setPhoneNumber(phoneNumber);

        if (password != null)
            user.setPassword(new BCryptPasswordEncoder().encode(password));

        if (profilePicture != null)
            saveProfilePicture(profilePicture, user);


        userRepository.save(user);
        return new ResponseState("success");
    }

    @RequestMapping(value = "/user/id/{id}")
    public User getUserById(@PathVariable Integer id) {
        User u = userRepository.getOne(id);
        if (u == null) {
            u = new User();
            u.setId(-1);
        }
        return u;
    }

    @RequestMapping(value = "/user/like/{string}")
    public List<User> getUserById(@PathVariable String string) {
        List<User> matches = userRepository.findTop5ByFullNameContaining(string);

        return matches;
    }


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
