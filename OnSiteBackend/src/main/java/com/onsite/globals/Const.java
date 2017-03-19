package com.onsite.globals;

/**
 * Created by TJR on 3/9/2017.
 */
public class Const {
    public static final String UPLOAD_PATH = "/files/";
    public static final String ROOT_PATH = "/onsite/files";
    public static final String HOST_URL = "http://localhost:8080/";
    public static final String DEFAULT_PROFILE_PIC = HOST_URL + "/images/get/default.jpg";
    public static final String FIREBASE_KEY = "AAAAZyfkSFg:APA91bHz6Tzndb3WlYtJEYxXa6uLLoWNwx3bqovwCvt7RAz2dt6x8OI4gxweGMxuiz_X5YaJz_wN7G43eAoMkhUWTqOBwmWZ2dmrmoEHgKhfmvCOQn99uozToc-wJpeMrKjX9qg_sOSt";
}


//TODO : done change login : Return userId + role
//TODO : done Change user sign in: Add parameter for role
//TODO : done Change All projects response: Alter responses for each user depending on username, add accepted attribute to project,task,issue
//TODO : done Contractor startup: Add new mapping for Contractor issues, tasks
//TODO : done Alter classes task, project, issue
//TODO : done Alter issue controller: Client:only reported by him , Manager:All, Contractor:Only Assigned
//TODO : done Alter Task Controller : Client,Mgr: All tasks , Contractor: Only Assigned
//TODO : task:done . Alter All controllers for recieving a user acceptance.

//TODO : Add email sending support, for password reset
//TODO : Change notification behavior