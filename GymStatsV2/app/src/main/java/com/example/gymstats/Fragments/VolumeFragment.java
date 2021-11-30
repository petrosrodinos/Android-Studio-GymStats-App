package com.example.gymstats.Fragments;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.gymstats.Data.Gym.Details;
import com.example.gymstats.Data.Gym.Info;
import com.example.gymstats.Other.createCharts;
import com.example.gymstats.Other.fileManager;
import com.example.gymstats.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private Spinner from,to;
    private Button ok;
    private SimpleDateFormat sdformat;
    private List<Details> temp = new ArrayList<>();
    private LineChart line;
    private BarChart bar;
    private int i;
    private List<String> datesfrom = new ArrayList<>();
    private List<String> datesto = new ArrayList<>();
    private String date="";

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
        line = view.findViewById(R.id.line_chart);
        bar = view.findViewById(R.id.barchart);
        from = view.findViewById(R.id.from);
        to = view.findViewById(R.id.to);
        ok = view.findViewById(R.id.ok);
        sdformat = new SimpleDateFormat("dd-MM-yyyy");
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

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    makeCharts();
                } catch (ParseException e) {

                }
            }
        });

        if(detailslist!=null){
            if(detailslist.size()>0){
                datesfrom.add(" FROM ");
                datesto.add("  TO  ");
                for(Details det:detailslist){
                    if(!datesfrom.contains(det.getDate())){
                        datesfrom.add(det.getDate());
                        datesto.add(det.getDate());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.customspinner, datesfrom);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(),R.layout.customspinner, datesto);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                from.setAdapter(adapter);
                to.setAdapter(adapter2);
            }
        }

        try {
            makeCharts();
        } catch (ParseException e) {
            Log.d("ggg",e.toString()+"");
        }


        return view;
    }

    private void makeCharts() throws ParseException {
        j=0;
        i=0;
        max=0;
        data.clear();
        bardata.clear();
        temp.clear();
        String fromdate = from.getSelectedItem().toString();
        String todate = to.getSelectedItem().toString();

        if(detailslist.size()>0){
            if(!fromdate.equals(" FROM ") && !todate.equals("  TO  ")){
                Date d1 = sdformat.parse(fromdate);
                Date d2 = sdformat.parse(todate);
                if(fromdate.equals(todate)){
                    data.clear();
                    bardata.clear();
                    for(Details det: detailslist){
                        if(det.getDate().equals(fromdate)){
//                            data.add(new Entry(++j,det.getReps()));
//                            bardata.add(new BarEntry(++j,det.getReps()));
                        }
                    }
                }
                else{
                    data.clear();
                    bardata.clear();
                    for(Details det: detailslist){
                        Date d3 = sdformat.parse(det.getDate());
                        if(d3.equals(d1) || d3.after(d1)){
                            if( (d3.equals(d2) || d3.before(d2))){
                                temp.add(det);
                            }
                        }
                    }
                    //temp.add(detailslist.get(0));
                    for(Details det:temp){
                        fullDateData(det);
                    }
                }
            }else if(fromdate.equals(" FROM ") && todate.equals("  TO  ")){
                for(Details det: detailslist){
                    fullData(det);
                }
            }else{
                Toast.makeText(getActivity(),"Select a valid date",Toast.LENGTH_SHORT).show();
            }
        }

        if(bardata.size()>0){
            createCharts create = new createCharts("Volume",line,bar,data,bardata);
            create.create();
            line.setVisibility(View.VISIBLE);
            bar.setVisibility(View.VISIBLE);
        }else{
            line.setVisibility(View.GONE);
            bar.setVisibility(View.GONE);
            Toast.makeText(getActivity(),"No data available for this period",Toast.LENGTH_SHORT).show();
        }
    }

    private void fullDateData(Details det){
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
        }
    }

    private void fullData(Details det){
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
