package com.example.adamsmith.alcdevelopers;

/**
 * Created by adamsmith on 10/10/2017.
 */
public class Developer {

    // profile image of the developer
    private final String mProfileimage;

    //Username of the developer
    private final String mUsername;

    //url
    private final String mUrl;

/*
   * Create a new developer object object.
   *
   *    * @param profileImage is the image of the developer
   * @param username is the username of the developer

   * */

    public Developer(String profileImage, String username, String url){
        mProfileimage = profileImage;
        mUsername = username;
        mUrl = url;
    }




    // get the profile image
    public String getProfileImage(){
        return mProfileimage;
    }

    // get username
    public  String getUsername(){
        return mUsername;
    }
//
   public String getUrl() {
        return mUrl;
    }
}

