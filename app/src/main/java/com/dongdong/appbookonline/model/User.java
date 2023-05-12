package com.dongdong.appbookonline.model;

import java.io.Serializable;

public class User implements Serializable {
    public  String User_ID , User_name, User_email,User_pass, User_descible, User_Img, User_Time ;

    public User(String user_ID, String user_name, String user_email, String user_pass, String user_descible, String user_Img, String user_Time) {
        User_ID = user_ID;
        User_name = user_name;
        User_email = user_email;
        User_pass = user_pass;
        User_descible = user_descible;
        User_Img = user_Img;
        User_Time = user_Time;
    }

    public User() {
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getUser_email() {
        return User_email;
    }

    public void setUser_email(String user_email) {
        User_email = user_email;
    }

    public String getUser_pass() {
        return User_pass;
    }

    public void setUser_pass(String user_pass) {
        User_pass = user_pass;
    }

    public String getUser_descible() {
        return User_descible;
    }

    public void setUser_descible(String user_descible) {
        User_descible = user_descible;
    }

    public String getUser_Img() {
        return User_Img;
    }

    public void setUser_Img(String user_Img) {
        User_Img = user_Img;
    }

    public String getUser_Time() {
        return User_Time;
    }

    public void setUser_Time(String user_Time) {
        User_Time = user_Time;
    }
}
