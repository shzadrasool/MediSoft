package com.example.myapplication.Retrofit;


import com.example.myapplication.User;
import com.example.myapplication.adapters.MediGetAdapter;
import com.example.myapplication.model_classes.mPic;

public class ServerRequest {
    private String operation;
    private User user;
    private mPic mpic;
    private MediGetAdapter mediGetAdapter;

    public void setMediGetAdapter(MediGetAdapter mediGetAdapter) {
        this.mediGetAdapter = mediGetAdapter;
    }

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
