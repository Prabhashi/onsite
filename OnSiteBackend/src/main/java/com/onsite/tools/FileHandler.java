package com.onsite.tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by TJR on 3/10/2017.
 */
public class FileHandler {
    public final static int SUCCESS = 1;
    public final static int FAIL = 2;
    public static int saveFile(String directory, String fileName, MultipartFile file){
        byte[] bytes ;
        try {
            bytes = file.getBytes();


            File dir = new File(directory);
            if (!dir.exists())
                dir.mkdirs();

            // Create the file on server

            File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            //issue.getImageUrls().add(Const.HOST_URL + "image/get/issue/" + serverFile.getName());
            return SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  FAIL;
    }
}
