package com.example.gymstats.Data.Gym;

public class Excercises {
    private String name;
    private int vsets;
    private int vreps;

    public Excercises(String name,int vsets,int vreps){
        this.name = name;
        this.vsets = vsets;
        this.vreps = vreps;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return vsets;
    }

    public void setSets(int vsets) {
        this.vsets = vsets;
    }

    public int getReps() {
        return vreps;
    }

    public void setReps(int vreps) {
        this.vreps = vreps;
    }


}
