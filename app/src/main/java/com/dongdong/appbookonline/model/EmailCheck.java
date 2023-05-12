package com.dongdong.appbookonline.model;

public class EmailCheck {
    private String email;
    private String pass;

    public EmailCheck(String email, String pass, String id) {
        this.email = email;
        this.pass = pass;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailCheck() {
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
