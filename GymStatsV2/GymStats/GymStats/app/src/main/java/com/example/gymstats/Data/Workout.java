package com.example.gymstats.Data;

import java.util.List;

public class Workout {
    private String day,name;
    private List<Excercises> excercise;


    public Workout(){    }

    public Workout(String day,String name,List<Excercises> excercises){
        this.day = day;
        this.name = name;
        this.excercise = excercises;
    }

    public String getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExcercise(List<Excercises> excercise) {
        this.excercise = excercise;
    }

    public List<Excercises> getExcercise(){
        return excercise;
    }


}
