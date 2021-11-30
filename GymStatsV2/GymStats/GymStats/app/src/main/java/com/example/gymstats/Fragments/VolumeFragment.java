package com.example.gymstats.Fragments;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.gymstats.Data.Details;
import com.example.gymstats.Data.Info;
import com.example.gymstats.Other.fileManager;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VolumeFragment extends Fragment{
    List<Entry> data = new ArrayList<>();
    List<ILineDataSet> datasets = new ArrayList<>();
    List<BarEntry> bardata = new ArrayList<>();
    String day;
    List<Info> jsondata = new ArrayList<>();
    fileManager fm;
    Gson gson;
    private List<Details> detailslist = new ArrayList<>();
    int max,j;

    public static VolumeFragment newInstance(String text) {
        VolumeFragment fragment = new VolumeFragment();
        Bundle args = new Bundle();
        args.putString("text", text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getString("text");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_volume, container, false);

        fm = new fileManager(getActivity().getApplicationContext());
        gson = new Gson();

        String res = fm.readFromFile(2);
        Type workoutday = new TypeToken<ArrayList<Info>>() {}.getType();
        jsondata = gson.fromJson(res, workoutday);

        for(Info currinfo:jsondata){
            if(currinfo.getName()!=null) {
                if (!currinfo.getName().isEmpty()){
                    if (currinfo.getName().equals(day)) {
                        if(currinfo.getDetails()!=null || currinfo.getDetails().size()>0){
                            detailslist = currinfo.getDetails();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
            }
        }

        LineChart line = view.findViewById(R.id.line_chart);
        BarChart bar = view.findViewById(R.id.barchart);

        if(detailslist.size()>0){
            int i=0;
            String date="";
            for(Details det:detailslist) {
                if(i==0){
                    max+=det.getReps()*det.getWeight();
                    date = det.getDate();
                    i++;
                    if(i==detailslist.size()){
                        data.add(new Entry(++j,max));
                        bardata.add(new BarEntry(++j,max));
                    }
                }else if(i>0 && det.getDate().equals(date)){
                    i++;
                    max+=det.getReps()*det.getWeight();
                    if(i==detailslist.size()){
                        data.add(new Entry(++j,max));
                        bardata.add(new BarEntry(++j,max));
                    }
                }else{
                    data.add(new Entry(++j,max));
                    bardata.add(new BarEntry(++j,max));
                    max=0;
                    max+=det.getReps()*det.getWeight();
                    date = det.getDate();
                    i++;
                    if(i==detailslist.size()){
                        data.add(new Entry(++j,max));
                        bardata.add(new BarEntry(++j,max));
                    }
                }
            }
        }

        if(bardata.size()>0){
            createCharts create = new createCharts("Volume",line,bar,bardata,data);
            create.create();
        }else{
            line.setVisibility(view.INVISIBLE);
            bar.setVisibility(view.INVISIBLE);
        }


        return view;
    }
}
