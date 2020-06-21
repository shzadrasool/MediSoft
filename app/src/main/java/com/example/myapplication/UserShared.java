package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class UserShared {

    private String name;
    private String contact;
    private String password;
    private String user_uid;


    Context context;
    SharedPreferences sharedPreferences;

    public UserShared(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    }


    public void removerUser() {
        sharedPreferences.edit().clear().apply();
    }


    public void setUser_uid(String user_uid) {
        sharedPreferences.edit().putString("user_uid", user_uid).apply();
        this.user_uid = user_uid;
    }

    public String getUser_uid() {
        user_uid = sharedPreferences.getString("user_uid", "");
        return user_uid;
    }


    public void setName(String name) {
        sharedPreferences.edit().putString("name", name).apply();
        this.name = name;
    }


    public String getName() {
        name = sharedPreferences.getString("name", "");
        return name;
    }

    public void setContact(String contact) {
        sharedPreferences.edit().putString("con", contact).apply();
        this.contact = contact;
    }

    public String getContact() {
        contact = sharedPreferences.getString("con", "");
        return contact;
    }


    public void setPassword(String password) {
        this.password = password;
    }


}
