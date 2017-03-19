package com.example.tjr.onsite.app;

import android.app.Application;

/**
 * Created by TJR on 2/24/2017.
 */

public class OnsiteApplication extends Application {
    public OnsiteApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyVolley.init(getApplicationContext());
    }
}
