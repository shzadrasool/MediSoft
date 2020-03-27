package com.example.myapplication.model_classes;

public class medi {
    String mid;
    String mediName;
    String mediMg;
    String mediPrice;
    String mediType;

    public medi(String mid, String mediName, String mediMg, String mediPrice, String mediType) {
        this.mid = mid;
        this.mediName = mediName;
        this.mediMg = mediMg;
        this.mediPrice = mediPrice;
        this.mediType = mediType;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMediName() {
        return mediName;
    }

    public void setMediName(String mediName) {
        this.mediName = mediName;
    }

    public String getMediMg() {
        return mediMg;
    }

    public void setMediMg(String mediMg) {
        this.mediMg = mediMg;
    }

    public String getMediPrice() {
        return mediPrice;
    }

    public void setMediPrice(String mediPrice) {
        this.mediPrice = mediPrice;
    }

    public String getMediType() {
        return mediType;
    }

    public void setMediType(String mediType) {
        this.mediType = mediType;
    }
}
