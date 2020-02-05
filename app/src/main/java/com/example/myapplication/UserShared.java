package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class UserShared {

    private String contact;
    private String password;

    Context context;
    SharedPreferences sharedPreferences;

    public UserShared(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    }


    public void removerUser() {
        sharedPreferences.edit().clear().apply();
    }


    public String getContact() {
        return contact;
    }

    public String getPassword() {
        return password;
    }


    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
