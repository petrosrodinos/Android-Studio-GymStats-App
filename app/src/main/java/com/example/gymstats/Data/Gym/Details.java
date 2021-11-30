package com.example.gymstats.Data.Gym;

public class Details {
    private float reps;
    private float weight;
    private String date,notes;


    public Details(float weight,float reps,String date,String notes){
        this.weight = weight;
        this.reps = reps;
        this.date = date;
        this.notes = notes;
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

    public float getReps() {
        return reps;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
