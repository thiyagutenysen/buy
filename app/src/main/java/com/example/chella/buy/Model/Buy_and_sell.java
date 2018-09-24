package com.example.chella.buy.Model;

import java.security.PublicKey;

public class Buy_and_sell {
    public String seller;
    public String title;
    public String contact;
    public String price;
    public String desc;
    public String timestamp;
    public String image;
    public String userid,key;

    public Buy_and_sell() {

    }

    public Buy_and_sell(String contact,String desc, String image, String key,  String price ,String seller, String timestamp,String title,  String userid) {
        this.title = title;
        this.contact= contact;
        this.price = price;
        this.desc = desc;
        this.key = key;
        this.seller = seller;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getSeller() { return seller;}

    public void setSeller(String seller) { this.seller = seller;}

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

    public String getKey() { return key; }

    public void setKey(String key) {
        this.key = key;
    }


}
