package com.dongdong.appbookonline.model;

import java.io.Serializable;

public class Stories implements Serializable {

    private  String Stories_Id;
    private  String Stories_Name;
    private  String Stories_IdAuth ;
    private  String Stories_Time ;
    private  String Stories_Status;
    private  String Stories_CtgrID ;
    private String Stories_Des;

    public String getStories_Des() {
        return Stories_Des;
    }

    public void setStories_Des(String stories_Des) {
        Stories_Des = stories_Des;
    }

    public String getStories_View() {
        return Stories_View;
    }

    public void setStories_View(String stories_View) {
        Stories_View = stories_View;
    }

    private String Stories_View;
    public  String Stories_Img;


    public Stories() {
    }

    public String getStories_Id() {
        return Stories_Id;
    }

    public void setStories_Id(String stories_Id) {
        Stories_Id = stories_Id;
    }

    public String getStories_Name() {
        return Stories_Name;
    }

    public void setStories_Name(String stories_Name) {
        Stories_Name = stories_Name;
    }

    public String getStories_IdAuth() {
        return Stories_IdAuth;
    }

    public void setStories_IdAuth(String stories_IdAuth) {
        Stories_IdAuth = stories_IdAuth;
    }

    public String getStories_Time() {
        return Stories_Time;
    }

    public void setStories_Time(String stories_Time) {
        Stories_Time = stories_Time;
    }

    public String getStories_Status() {
        return Stories_Status;
    }

    public void setStories_Status(String stories_Status) {
        Stories_Status = stories_Status;
    }

    public String getStories_CtgrID() {
        return Stories_CtgrID;
    }

    public void setStories_CtgrID(String stories_CtgrID) {
        Stories_CtgrID = stories_CtgrID;
    }

    public String getStories_Img() {
        return Stories_Img;
    }

    public void setStories_Img(String stories_Img) {
        Stories_Img = stories_Img;
    }

}
