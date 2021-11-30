package com.example.gymstats.Data;

public class Excercises {
    private int id;
    private String name;
    private int vsets;
    private int vreps;

    public Excercises(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
