package com.example.myapplication.Retrofit;


import com.example.myapplication.User;
import com.example.myapplication.model_classes.mPic;

public class ServerRequest {
    private String operation;
    private User user;
    private mPic mpic;


    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMpic(mPic mpic) {
        this.mpic = mpic;
    }
}
