package com.example.gymstats.Data.SwimmingCycling;

public class swimmingCyclingdetails {
    private float distance,time;
    private String date;


    public swimmingCyclingdetails(float distance,float time,String date){
        this.distance = distance;
        this.time = time;
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTime() {
        return time;
    }

}
