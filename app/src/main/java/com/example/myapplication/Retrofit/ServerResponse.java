package com.example.myapplication.Retrofit;

import com.example.myapplication.User;

public class ServerResponse {
    private String result;
    private String message;
    private User user;


    public User getUser() {
        return user;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
