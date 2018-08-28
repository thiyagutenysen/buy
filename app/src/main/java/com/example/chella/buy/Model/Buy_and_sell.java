package com.example.chella.buy.Model;

public class Buy_and_sell {
    public String title;
    public String price;
    public String desc;
    public String timestamp;
    public String image;
    public String userid;

    public Buy_and_sell() {

    }

    public Buy_and_sell(String desc, String image,  String price , String timestamp,String title,  String userid) {
        this.title = title;
        this.price = price;
        this.desc = desc;
        this.timestamp = timestamp;
        this.image = image;
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
