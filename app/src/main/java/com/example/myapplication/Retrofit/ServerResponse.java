package com.example.myapplication.Retrofit;

import com.example.myapplication.User;
import com.example.myapplication.adapters.MediCommonGetAdapter;
import com.example.myapplication.adapters.MediGetAdapter;
import com.example.myapplication.model_classes.mPic;

public class ServerResponse {
    private String result;
    private String message;
    private User user;
    private mPic m;
    private MediGetAdapter mediGetAdapter;
    private MediCommonGetAdapter mediGetCommonAdapter;

    public MediGetAdapter getMediGetAdapter() {
        return mediGetAdapter;
    }

    public MediCommonGetAdapter getMediGetCommonAdapter() {
        return mediGetCommonAdapter;
    }

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
