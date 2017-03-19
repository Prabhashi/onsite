package com.example.tjr.onsite.toolbox;

import java.io.File;

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
}
