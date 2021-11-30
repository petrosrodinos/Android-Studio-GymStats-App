package com.example.gymstats.Data.Time;

public class timeDetails {
    float time;
    private String date;

    public timeDetails(float time,String date){
        this.time = time;
        this.date = date;

    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
