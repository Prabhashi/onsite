package com.onsite.tools;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by TJR on 3/24/2017.
 */
public class ImageDecoder {
    public String decodeAndSave(String prefix,String image){

        byte bytes[] = Base64.decodeBase64(image);
        FileOutputStream fos = null;

        long l = System.currentTimeMillis();
        String fileName = prefix+"_"+l;
        try{

            File file = new File(fileName+".jpg");
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
            return fileName;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
