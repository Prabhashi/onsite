package com.onsite.controllers;

import com.onsite.model.ResponseState;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by TJR on 3/9/2017.
 */
@RestController
public class FileUploadController {

    private final static  String UPLOADED_FOLDER = "/files/pictures/";


    @RequestMapping(value = "/upload/", method = RequestMethod.POST, produces = "application/json")
    public ResponseState singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {

            return new ResponseState("empty file");
        }

        try {



            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + "sheldon.jpg");

            Files.write(path, bytes);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseState("success");
    }

/*
    public void saveprofileimage(MultipartFile profilePic, UserTmp tmpuser,User user) {
        try {

            byte[] bytes = profilePic.getBytes();
            // Creating the directory to store file
            // String rootPath = System.getProperty("catalina.home");
//			 String rootPath = "C:\\image2";

            String rootPath = "/var/connectplace/images/";

            File dir = new File(rootPath + File.separator + "ProfilePictures");
            if (!dir.exists())
                dir.mkdirs();

            // Create the file on server

            String name = profilePic.getOriginalFilename().replace("%20", "-");
            String arrayName = name.substring(0, name.indexOf("."));
            name = arrayName + ".PNG";

            if(tmpuser!=null){

                System.out.println("|||||| tmpuser not null ");
                File serverFile = new File(dir.getAbsolutePath() + File.separator + tmpuser.getUsername() + "-"
                        + System.currentTimeMillis() + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream( serverFile));
                stream.write(bytes);
                stream.close();
                tmpuser.setUserProfilePicture("http://54.218.66.18:8080/connectplace/api/getpicture?profile_picture_name="+serverFile.getName());
            }
            else if(user!=null){

                System.out.println("|||||| user not null ");
                File serverFile = new File(dir.getAbsolutePath() + File.separator + user.getUsername() + "-"
                        + System.currentTimeMillis() + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                user.setUserProfilePicture("http://54.218.66.18:8080/connectplace/api/getpicture?profile_picture_name="+serverFile.getName());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }*/
}
