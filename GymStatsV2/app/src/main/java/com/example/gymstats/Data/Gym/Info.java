package com.example.gymstats.Data.Gym;

import java.util.List;

public class Info {
    private String name;
    private List<Details> details;

    public Info(String name,List<Details> details){
        this.name = name;
        this.details = details;
    }

    public Info(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }



}
