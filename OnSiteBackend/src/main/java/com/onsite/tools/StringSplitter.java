package com.onsite.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TJR on 3/10/2017.
 */
public class StringSplitter {
    public static List<Integer> splitIntoInts(String s, String delimiter){
        List<Integer> list = new ArrayList<>();

        if(s==null)return list;

        String [] tokens = s.split(delimiter);
        for(String str: tokens)
            list.add(Integer.valueOf(str));
        return list;
    }
}
