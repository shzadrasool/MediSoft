package com.example.myapplication.Retrofit;

import com.example.myapplication.User;
import com.example.myapplication.model_classes.mPic;

public class ServerResponse {
    private String result;
    private String message;
    private User user;
    private mPic m;


    public User getUser() {
        return user;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public mPic getM() {
        return m;
    }
}
