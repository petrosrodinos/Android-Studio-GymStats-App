package com.example.gymstats.Data.Calories;

public class Calories {
    int calories,protein,carbs,fats;
    String name;

    public Calories(String name,int calories,int protein,int carbs,int fat){
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fat;
        this.name = name;
    }

    public Calories(String name,int calories){
        this.name = name;
        this.calories = calories;
    }

    public Calories(String name,int protein,int carbs,int fats){
        this.name = name;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public Calories(){

    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getFats() {
        return fats;
    }

    public String getName() {
        return name;
    }
}
