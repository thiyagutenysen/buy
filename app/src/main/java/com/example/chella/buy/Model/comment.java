package com.example.chella.buy.Model;

public class comment {
    public String comment;
    public String userid;

    public comment() {

    }
    public comment(String comment, String userid) {
        this.comment = comment;
        this.userid=userid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

