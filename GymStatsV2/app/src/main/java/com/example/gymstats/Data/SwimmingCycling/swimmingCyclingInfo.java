package com.example.gymstats.Data.SwimmingCycling;

import java.util.List;

public class swimmingCyclingInfo {
    private String name;
    private List<swimmingCyclingdetails> details;

    public swimmingCyclingInfo(String name,List<swimmingCyclingdetails> details){
        this.name = name;
        this.details = details;
    }

    public swimmingCyclingInfo(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<swimmingCyclingdetails> getDetails() {
        return details;
    }

    public void setDetails(List<swimmingCyclingdetails> details) {
        this.details = details;
    }



}
