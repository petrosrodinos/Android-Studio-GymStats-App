package com.example.gymstats.Data;

public class Details {
    private int reps;
    private float weight;
    private String date;


    public Details(float weight,int reps,String date){
        this.weight = weight;
        this.reps = reps;
        this.date = date;
    }

    public Details(float weight,int reps){
        this.weight = weight;
        this.reps = reps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
