package com.example.gymstats.Other;
import android.graphics.Color;
import com.example.gymstats.R;
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
    LineChart line;
    BarChart bar;
    List<Entry> data;
    List<BarEntry> bardata;
    List<ILineDataSet> datasets = new ArrayList<>();
    BarDataSet barDataSet;

    public createCharts(String name,LineChart line,BarChart bar,List<Entry> data,List<BarEntry> bardata){
        this.name = name;
        this.line = line;
        this.bar = bar;
        this.data = data;
        this.bardata = bardata;
    }

    public createCharts(){

    }

    public void create(){
        Description description1 = new Description();
        description1.setText("Line Chart");
        description1.setTextSize(10);

        LineDataSet dataset = new LineDataSet(data,name);
        dataset.setColor(Color.rgb(255,64,0));
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

        barDataSet = new BarDataSet(bardata,name);
        barDataSet.setColor(Color.rgb(255,64,0));

        BarData bardata = new BarData();
        bardata.addDataSet(barDataSet);
        bar.setDescription(description);
        bar.setDrawGridBackground(true);
        bar.setData(bardata);
        bar.animateXY(1000,1000);
        bar.invalidate();
    }

}
