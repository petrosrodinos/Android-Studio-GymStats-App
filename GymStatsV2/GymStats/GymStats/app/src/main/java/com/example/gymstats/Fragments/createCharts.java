package com.example.gymstats.Fragments;
import android.graphics.Color;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class createCharts {
    String name;
    List<Entry> data;
    List<ILineDataSet> datasets = new ArrayList<>();
    List<BarEntry> bardata;
    LineChart line;
    BarChart bar;


    public createCharts(String name,LineChart line,BarChart bar,List<BarEntry> bardata,List<Entry> data){
        this.name = name;
        this.bar = bar;
        this.line = line;
        this.bardata = bardata;
        this.data = data;

    }

    public void create(){
        Description description1 = new Description();
        description1.setText("Line Chart");
        description1.setTextSize(10);

        LineDataSet dataset = new LineDataSet(data,name);
        dataset.setColor(Color.RED);
        datasets.add(dataset);

        LineData dat = new LineData(datasets);
        line.setData(dat);
        line.setDescription(description1);
        line.setDrawGridBackground(true);
        line.animateXY(1000,1000);
        line.invalidate();

        Description description = new Description();
        description.setText("Bar Chart");
        description.setTextSize(10);
        description.setTextColor(Color.WHITE);

        BarDataSet barDataSet = new BarDataSet(bardata,name);
        barDataSet.setColor(Color.RED);

        BarData bardata = new BarData();
        bardata.addDataSet(barDataSet);
        bar.setDescription(description);
        bar.setData(bardata);
        bar.animateXY(1000,1000);
        bar.invalidate();
    }


}
