package com.example.myapplication.model_classes;


public class mPic {

    private String address;
    private String extras;
    private String picName;
    private String image_code;
    private String user_uid;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getImage_code() {
        return image_code;
    }

    public void setImage_code(String image_code) {
        this.image_code = image_code;
    }
}
