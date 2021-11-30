package com.example.gymstats.Other;
import android.content.Context;
import android.content.SharedPreferences;
import com.example.gymstats.Data.Calories.CalorieData;
import com.example.gymstats.Data.Calories.Calories;
import com.example.gymstats.Data.Distance.distanceDetails;
import com.example.gymstats.Data.Distance.distanceInfo;
import com.example.gymstats.Data.Gym.Details;
import com.example.gymstats.Data.Gym.Excercises;
import com.example.gymstats.Data.Gym.Info;
import com.example.gymstats.Data.Gym.Workout;
import com.example.gymstats.Data.Scale.scaleDetails;
import com.example.gymstats.Data.Scale.scaleInfo;
import com.example.gymstats.Data.SwimmingCycling.swimmingCyclingInfo;
import com.example.gymstats.Data.SwimmingCycling.swimmingCyclingdetails;
import com.example.gymstats.Data.Time.timeDetails;
import com.example.gymstats.Data.Time.timeInfo;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;

public class sampleData {
    fileManager fm;
    public List<Excercises> excercises = new ArrayList<>();
    List<Details> details = new ArrayList<>();
    public List<Info> infos = new ArrayList<>();

    List<distanceDetails> distance = new ArrayList<>();
    public List<distanceInfo> distanceinfo = new ArrayList<>();

    List<timeDetails> time = new ArrayList<>();
    public List<timeInfo> timeinfo = new ArrayList<>();

    List<scaleDetails> scale = new ArrayList<>();
    public List<scaleInfo> scaleinfo = new ArrayList<>();

    List<swimmingCyclingdetails> swimmingcycling = new ArrayList<>();
    public List<swimmingCyclingInfo> swimmingcyclinginfo = new ArrayList<>();

    List<Calories> calories = new ArrayList<>();

    List<CalorieData> caloriedata = new ArrayList<>();

    List<Workout> workouts = new ArrayList<>();
    Gson gson;
    private Context ctx;

    public sampleData(Context ctx){
        gson = new Gson();
        fm = new fileManager(ctx);
        this.ctx = ctx;
    }

    public void addSampleData(){
        for(int i=1;i<=8;i++){
            restoreData(i);
        }
    }

    public void restoreData(int num){
        if(num==1){
            fm.writeToFile(1,"");
            workouts.add(new Workout("monday","",excercises));
            workouts.add(new Workout("tuesday","",excercises));
            workouts.add(new Workout("wednesday","",excercises));
            workouts.add(new Workout("thursday","",excercises));
            workouts.add(new Workout("friday","",excercises));
            workouts.add(new Workout("saturday","",excercises));
            workouts.add(new Workout("sunday","",excercises));
            String workout = gson.toJson(workouts);
            fm.writeToFile(1,workout);
        }else if(num==2){
            fm.writeToFile(2,"");
            infos.add(new Info("Sample",details));
            String infostring = gson.toJson(infos);
            fm.writeToFile(2,infostring);
        }else if(num==3){
            fm.writeToFile(3,"");
            timeinfo.add(new timeInfo("Sample",time));
            String time = gson.toJson(timeinfo);
            fm.writeToFile(3,time);
        }else if(num==4){
            fm.writeToFile(4,"");
            distanceinfo.add(new distanceInfo("Sample",distance));
            String distance = gson.toJson(distanceinfo);
            fm.writeToFile(4,distance);
        }else if(num==5){
            fm.writeToFile(5,"");
            scaleinfo.add(new scaleInfo("Sample",scale));
            String scale = gson.toJson(scaleinfo);
            fm.writeToFile(5,scale);
        }else if(num==6){
            fm.writeToFile(6,"");
            swimmingcyclinginfo.add(new swimmingCyclingInfo("Sample",swimmingcycling));
            String swimmingcycling = gson.toJson(swimmingcyclinginfo);
            fm.writeToFile(6,swimmingcycling);
        }else if(num==7){
            fm.writeToFile(7,"");
            calories.add(new Calories());
            String caloriesdata = gson.toJson(calories);
            fm.writeToFile(7,caloriesdata);
        }else if(num==8){
            fm.writeToFile(8,"");
            caloriedata.add(new CalorieData());
            String caloriesdata2 = gson.toJson(caloriedata);
            fm.writeToFile(8,caloriesdata2);
        }
    }

    public void addPreferences() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstrun",false);
        editor.apply();
        addSampleData();
    }
}
