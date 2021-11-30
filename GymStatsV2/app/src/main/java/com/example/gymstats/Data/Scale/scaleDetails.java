package com.example.gymstats.Data.Scale;

public class scaleDetails {
    float weight;
    private String date;

    public scaleDetails(float weight,String date){
        this.weight = weight;
        this.date = date;

    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

