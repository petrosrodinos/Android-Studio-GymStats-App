package com.example.gymstats.Fragments;
import android.annotation.SuppressLint;
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

public class WeightFragment extends Fragment{
    private List<Entry> data = new ArrayList<>();
    private List<BarEntry> bardata = new ArrayList<>();
    private List<String> datesfrom = new ArrayList<>();
    private List<String> datesto = new ArrayList<>();
    private String day;
    private List<Info> jsondata = new ArrayList<>();
    private fileManager fm;
    private Gson gson;
    private List<Details> detailslist = new ArrayList<>();
    private List<Details> temp = new ArrayList<>();
    private int j,i;
    private float max;
    private Spinner from,to;
    private Button ok;
    private LineChart line;
    private BarChart bar;
    private String date="";
    private createCharts create;
    private SimpleDateFormat sdformat;

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

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    makeCharts();
                } catch (ParseException e) {
                    Log.d("ggg","edo");
                }
            }
        });

        for(Info currinfo:jsondata){
            if(currinfo.getName()!=null) {
                if (!currinfo.getName().isEmpty()){
                    if (currinfo.getName().equals(day)) {
                        if(currinfo.getDetails()!=null || currinfo.getDetails().size()>0){
                            detailslist = currinfo.getDetails();
                            break;
                        }
                    }
                }
            }
        }

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
        max=0;
        i=0;
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
                            data.add(new Entry(++j,det.getWeight()));
                            bardata.add(new BarEntry(++j,det.getWeight()));
                        }
                    }
                }else{
                    for(Details det: detailslist){
                        Date d3 = sdformat.parse(det.getDate());
                        if(d3.equals(d1) || d3.after(d1)){
                            if( (d3.equals(d2) || d3.before(d2))){
                                temp.add(det);
                            }
                        }
                    }
                    if(temp.size()>0){
                        temp.add(detailslist.get(0));
                        for(Details det:temp){
                            Log.d("ggg","send: "+det.getWeight());
                            fullDateData(det);
                        }
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
            create = new createCharts("Weight",line,bar,data,bardata);
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
            i++;
            if(det.getWeight()>max){
                max = det.getWeight();
                date = det.getDate();
            }
            if(i==temp.size()){
                data.add(new Entry(++j,max));
                bardata.add(new BarEntry(++j,max));
            }
        }else if(i>0 && det.getDate().equals(date)){
            i++;
            if(i==temp.size()){
                if(det.getWeight()>max){
                    max = det.getWeight();
                    data.add(new Entry(++j,max));
                    bardata.add(new BarEntry(++j,max));
                }else{
                    data.add(new Entry(++j,max));
                    bardata.add(new BarEntry(++j,max));
                }
            }
            if(det.getWeight()>max){
                max = det.getWeight();
            }
        }else{
            data.add(new Entry(++j,max));
            bardata.add(new BarEntry(++j,max));

            date = det.getDate();
            i++;
            max=det.getWeight();

        }
    }

    private void fullData(Details det){
        if(i==0){
            i++;
            if(det.getWeight()>max){
                //Log.d("ggg","1: "+det.getWeight()+">"+max);
                max = det.getWeight();
                date = det.getDate();
            }
            if(i==detailslist.size()){
                //Log.d("ggg","first and last");
                //Log.d("ggg","pushed 1: "+max);
                data.add(new Entry(++j,max));
                bardata.add(new BarEntry(++j,max));
            }
        }else if(i>0 && det.getDate().equals(date)){
            i++;
            if(i==detailslist.size()){
                //Log.d("ggg","last from many");
                if(det.getWeight()>max){
                    max = det.getWeight();
                    data.add(new Entry(++j,max));
                    bardata.add(new BarEntry(++j,max));
                    //Log.d("ggg","2: "+det.getWeight()+">"+max);
                    //Log.d("ggg","pushed 2: "+max);
                }else{
                    data.add(new Entry(++j,max));
                    bardata.add(new BarEntry(++j,max));
                    //Log.d("ggg","pushed 3: "+max);
                }
            }
            if(det.getWeight()>max){
                //Log.d("ggg","3: "+det.getWeight()+">"+max);
                max = det.getWeight();
            }
        }else{
            //Log.d("ggg","pushed 4: "+max);
            data.add(new Entry(++j,max));
            bardata.add(new BarEntry(++j,max));

            date = det.getDate();
            i++;
            if(i==detailslist.size()){
                //Log.d("ggg","last and last");
                //Log.d("ggg","pushed 5: "+det.getWeight());
                data.add(new Entry(++j,det.getWeight()));
                bardata.add(new BarEntry(++j,det.getWeight()));
            }
            max=det.getWeight();
            //Log.d("ggg","max: "+max);

        }
    }
}
