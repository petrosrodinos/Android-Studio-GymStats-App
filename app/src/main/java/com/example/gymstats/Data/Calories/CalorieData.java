package com.example.gymstats.Data.Calories;

public class CalorieData {
    int calories,protein,carbs,fats;

    public CalorieData(int calories,int protein,int carbs,int fats){
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;

    }

    public CalorieData(){

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

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }
}
