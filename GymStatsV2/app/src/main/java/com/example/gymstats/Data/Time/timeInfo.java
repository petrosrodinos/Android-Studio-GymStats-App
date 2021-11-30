package com.example.gymstats.Data.Time;

import java.util.List;

public class timeInfo {
    String name;
    private List<timeDetails> details;

    public timeInfo(String name, List<timeDetails> details){
        this.name = name;
        this.details = details;
    }

    public timeInfo(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<timeDetails> getDetails() {
        return details;
    }

    public void setDetails(List<timeDetails> details){
        this.details = details;
    }
}
