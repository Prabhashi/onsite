package com.example.tjr.onsite.toolbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by TJR on 3/13/2017.
 */

public class Data {
    String[] leftNavigationItemTextArray = {"Navigation","Item Bar"};
    File STORAGE_PATH = new File("img");
    public File getSTORAGE_PATH() {
        return STORAGE_PATH;
    }

    public String[] getLeftNavigationItemTextArray() {
        return leftNavigationItemTextArray;
    }



    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
