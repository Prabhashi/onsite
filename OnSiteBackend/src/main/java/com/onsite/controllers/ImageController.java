package com.onsite.controllers;

import com.onsite.globals.Const;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by TJR on 1/3/2017.
 */
@RestController
public class ImageController {

    @RequestMapping(value ="/create" , method = RequestMethod.GET)
    public void makeFile() throws IOException {
        File file = new File("somefile.txt");
        file.createNewFile();
        System.err.println(file.getPath());
        System.err.println(file.getAbsolutePath());
    }



    @RequestMapping(value = "images/get/{purpose}/{imageName}")
    public byte[] getProfilePicture(@PathVariable String imageName, @PathVariable String purpose){
        String url = "/ProfilePictures" + File.separator + imageName;
        switch (purpose){
            case "profile":
                return getImage("ProfilePictures"+File.separator +imageName);
            case "plan":
                return getImage("PlanImages" + File.separator + imageName);

            case "issue":
                return getImage("IssueImages" + File.separator + imageName);

        }
        return  getImage(url);
    }


    byte [] getImage(String url){
        try {

            url = url + ".jpg";
            File file = new File("");
            // Retrieve image from the classpath.
            InputStream is = new FileInputStream(url);

            if(is == null )
                System.err.println("NULL image");
            // Prepare buffered image.
            BufferedImage img = ImageIO.read(is);

            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            // Write to output stream
            ImageIO.write(img, "jpg", bao);

            return bao.toByteArray();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
