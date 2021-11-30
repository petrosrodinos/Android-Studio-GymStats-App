package com.example.gymstats.Data.Scale;

import java.util.List;

public class scaleInfo {
    String name;
    private List<scaleDetails> details;

    public scaleInfo(String name, List<scaleDetails> details){
        this.name = name;
        this.details = details;
    }

    public scaleInfo(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<scaleDetails> getDetails() {
        return details;
    }

    public void setDetails(List<scaleDetails> details){
        this.details = details;
    }
}
