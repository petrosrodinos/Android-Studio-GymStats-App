package com.example.gymstats.Data.Distance;

public class distanceDetails {
    float distance;
    private String date;

    public distanceDetails(float distance,String date){
        this.distance = distance;
        this.date = date;

    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
