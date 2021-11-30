package com.example.gymstats.Fragments;
import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WeightFragment extends Fragment{
    List<Entry> data = new ArrayList<>();
    List<BarEntry> bardata = new ArrayList<>();
    String day;
    List<Info> jsondata = new ArrayList<>();
    fileManager fm;
    Gson gson;
    private List<Details> detailslist = new ArrayList<>();
    int j;
    float max;

    public static WeightFragment newInstance(String text) {
        WeightFragment fragment = new WeightFragment();
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

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_weight, container, false);

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
                    i++;
                    if(det.getWeight()>max){
                        //Log.d("ggg","1: "+det.getWeight()+">"+max);
                        max = det.getWeight();
                        date = det.getDate();
                    }
                    if(i==detailslist.size()){
                        //Log.d("ggg","first and last");
                        data.add(new Entry(++j,max));
                        bardata.add(new BarEntry(++j,max));
                    }
                }else if(i>0 && det.getDate().equals(date)){
                    i++;
                    if(i==detailslist.size()){
                        //Log.d("ggg","last from many");
                        if(det.getWeight()>max){
                            //Log.d("ggg","2: "+det.getWeight()+">"+max);
                            max = det.getWeight();
                            data.add(new Entry(++j,max));
                            bardata.add(new BarEntry(++j,max));
                        }else{
                            data.add(new Entry(++j,max));
                            bardata.add(new BarEntry(++j,max));
                        }
                    }
                    if(det.getWeight()>max){
                        //Log.d("ggg","3: "+det.getWeight()+">"+max);
                        max = det.getWeight();
                    }
                }else{
                    //Log.d("ggg","CHANGED");
                    data.add(new Entry(++j,max));
                    bardata.add(new BarEntry(++j,max));

                    date = det.getDate();
                    i++;
                    if(i==detailslist.size()){
                        //Log.d("ggg","last and last");
                        data.add(new Entry(++j,det.getWeight()));
                        bardata.add(new BarEntry(++j,det.getWeight()));
                        break;
                    }
                    max=det.getWeight();
                }
            }
        }

        if(bardata.size()>0){
            createCharts create = new createCharts("Weight",line,bar,bardata,data);
            create.create();
        }else{
            line.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.INVISIBLE);
        }


        return view;
    }
}
