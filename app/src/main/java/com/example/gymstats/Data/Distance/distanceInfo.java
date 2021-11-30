package com.example.gymstats.Data.Distance;

import java.util.List;

public class distanceInfo {
    String name;
    private List<distanceDetails> details;

    public distanceInfo(String name, List<distanceDetails> details){
        this.name = name;
        this.details = details;
    }

    public distanceInfo(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<distanceDetails> getDetails() {
        return details;
    }

    public void setDetails(List<distanceDetails> details){
        this.details = details;
    }
}
