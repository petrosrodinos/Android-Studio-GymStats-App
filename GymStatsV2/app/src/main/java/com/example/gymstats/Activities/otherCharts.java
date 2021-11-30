package com.example.gymstats.Activities;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.gymstats.Data.Distance.distanceDetails;
import com.example.gymstats.Data.Distance.distanceInfo;
import com.example.gymstats.Data.Gym.Info;
import com.example.gymstats.Data.Scale.scaleDetails;
import com.example.gymstats.Data.Scale.scaleInfo;
import com.example.gymstats.Data.SwimmingCycling.swimmingCyclingInfo;
import com.example.gymstats.Data.SwimmingCycling.swimmingCyclingdetails;
import com.example.gymstats.Data.Time.timeDetails;
import com.example.gymstats.Data.Time.timeInfo;
import com.example.gymstats.Other.createCharts;
import com.example.gymstats.Other.fileManager;
import com.example.gymstats.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.view.View.INVISIBLE;

public class otherCharts extends AppCompatActivity {
    List<Entry> data = new ArrayList<>();
    List<BarEntry> bardata = new ArrayList<>();
    private List<timeDetails> timedetails = new ArrayList<>();
    private List<distanceDetails> distancedetails = new ArrayList<>();
    private List<scaleDetails> scaledetails = new ArrayList<>();
    private List<swimmingCyclingdetails> swimmingcyclingdetails = new ArrayList<>();
    List<timeInfo> timedata = new ArrayList<>();
    List<distanceInfo> distancedata = new ArrayList<>();
    List<scaleInfo> weightdata = new ArrayList<>();
    List<swimmingCyclingInfo> timedistancedata = new ArrayList<>();
    String day;
    fileManager fm;
    Gson gson;
    int j;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_charts);

        Intent intent = getIntent();
        day = intent.getStringExtra("excercise");
        Objects.requireNonNull(getSupportActionBar()).setTitle(day);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LineChart line = findViewById(R.id.line_chart);
        BarChart bar = findViewById(R.id.barchart);

        fm = new fileManager(this);
        gson = new Gson();

        if(day.equals("CYCLING") || day.equals("SWIMMING")){
            String timedistance = fm.readFromFile(6);
            Type timedistanceday = new TypeToken<ArrayList<swimmingCyclingInfo>>() {}.getType();
            timedistancedata = gson.fromJson(timedistance, timedistanceday);

            for(swimmingCyclingInfo info: timedistancedata){
                if(info!=null){
                    if(info.getName().equals(day)){
                        swimmingcyclingdetails = info.getDetails();
                    }
                }
            }
            if(swimmingcyclingdetails.size()>0){
                for(swimmingCyclingdetails det:swimmingcyclingdetails){
                    if(det!=null){
                        data.add(new Entry(++j,det.getTime()));
                        bardata.add(new BarEntry(++j,det.getTime()));
                    }
                }
            }

        }else if(day.equals("LONG JUMP") || day.equals("POLE VAULT") || day.equals("JAVELIN") || day.equals("SHOT PUT")){
            String distance = fm.readFromFile(4);
            Type distanceday = new TypeToken<ArrayList<distanceInfo>>() {}.getType();
            distancedata = gson.fromJson(distance, distanceday);

            for(distanceInfo info: distancedata){
                if(info.getName().equals(day)){
                    distancedetails = info.getDetails();
                }
            }
            if(distancedetails.size()>0){
                for(distanceDetails det:distancedetails){
                    if(det!=null){
                        data.add(new Entry(++j,det.getDistance()));
                        bardata.add(new BarEntry(++j,det.getDistance()));
                    }
                }
            }


        }else if(day.equals("SCALE")){
            String weight = fm.readFromFile(5);
            Type weightday = new TypeToken<ArrayList<scaleInfo>>() {}.getType();
            weightdata = gson.fromJson(weight, weightday);

            for(scaleInfo info: weightdata){
                if(info.getName().equals(day)){
                    scaledetails = info.getDetails();
                }
            }
            if(scaledetails.size()>0){
                for(scaleDetails det:scaledetails){
                    data.add(new Entry(++j,det.getWeight()));
                    bardata.add(new BarEntry(++j,det.getWeight()));
                }
            }

        }else{
            String time = fm.readFromFile(3);
            Type timeday = new TypeToken<ArrayList<timeInfo>>() {}.getType();
            timedata = gson.fromJson(time, timeday);

            for(timeInfo info: timedata){
                if(info.getName().equals(day)){
                    timedetails = info.getDetails();
                }
            }
            if(timedetails.size()>0){
                for(timeDetails det:timedetails){
                    data.add(new Entry(++j,det.getTime()));
                    bardata.add(new BarEntry(++j,det.getTime()));
                    //Log.d("ggg",det.getTime()+"");
                }
            }
        }

        if(bardata.size()>0){
            createCharts create = new createCharts(day,line,bar,data,bardata);
            create.create();
        }else{
            line.setVisibility(INVISIBLE);
            bar.setVisibility(INVISIBLE);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return super.onOptionsItemSelected(item);
    }
}
