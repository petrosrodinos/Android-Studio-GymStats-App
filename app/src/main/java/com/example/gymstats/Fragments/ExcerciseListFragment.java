package com.example.gymstats.Fragments;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.gymstats.Activities.Charts;
import com.example.gymstats.Activities.otherCharts;
import com.example.gymstats.Data.Distance.distanceInfo;
import com.example.gymstats.Data.Gym.Info;
import com.example.gymstats.Data.Scale.scaleInfo;
import com.example.gymstats.Data.SwimmingCycling.swimmingCyclingInfo;
import com.example.gymstats.Data.Time.timeInfo;
import com.example.gymstats.Other.fileManager;
import com.example.gymstats.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class ExcerciseListFragment extends Fragment {
    private ListView listview;
    List<Info> jsondata = new ArrayList<>();
    List<timeInfo> timedata = new ArrayList<>();
    List<distanceInfo> distancedata = new ArrayList<>();
    List<scaleInfo> weightdata = new ArrayList<>();
    List<swimmingCyclingInfo> timedistancedata = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    List<String> excelist = new ArrayList<>();
    List<String> otherlist = new ArrayList<>();
    fileManager fm;
    Gson gson;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_excercise_list, container, false);
        listview = view.findViewById(R.id.listview3);
        fm = new fileManager(Objects.requireNonNull(getActivity()).getApplicationContext());
        gson = new Gson();

        String res = fm.readFromFile(2);
        String time = fm.readFromFile(3);
        String distance = fm.readFromFile(4);
        String weight = fm.readFromFile(5);
        String timedistance = fm.readFromFile(6);

        Type workoutday = new TypeToken<ArrayList<Info>>() {}.getType();
        jsondata = gson.fromJson(res, workoutday);

        Type timeday = new TypeToken<ArrayList<timeInfo>>() {}.getType();
        timedata = gson.fromJson(time, timeday);

        Type distanceday = new TypeToken<ArrayList<distanceInfo>>() {}.getType();
        distancedata = gson.fromJson(distance, distanceday);

        Type weightday = new TypeToken<ArrayList<scaleInfo>>() {}.getType();
        weightdata = gson.fromJson(weight, weightday);

        Type timedistanceday = new TypeToken<ArrayList<swimmingCyclingInfo>>() {}.getType();
        timedistancedata = gson.fromJson(timedistance, timedistanceday);

        if(jsondata !=null) {
            if (jsondata.size() > 0) {
                for (Info info : jsondata) {
                    if(info.getDetails()!=null){
                        if(info.getDetails().size()>0){
                            if (!info.getName().equals("Sample") && !excelist.contains(info.getName())) {
                                excelist.add(info.getName());
                            }
                        }
                    }
                }
            }
        }

        if(timedata !=null) {
            if (timedata.size() > 0) {
                for (timeInfo info : timedata) {
                    if(info.getDetails()!=null){
                        if(info.getDetails().size()>0){
                            if (!info.getName().equals("Sample") && !excelist.contains(info.getName())) {
                                excelist.add(info.getName());
                                otherlist.add(info.getName());
                            }
                        }
                    }
                }
            }
        }

        if(distancedata !=null) {
            if (distancedata.size() > 0) {
                for (distanceInfo info : distancedata) {
                    if(info.getDetails()!=null){
                        if(info.getDetails().size()>0){
                            if (!info.getName().equals("Sample") && !excelist.contains(info.getName())) {
                                excelist.add(info.getName());
                                otherlist.add(info.getName());
                            }
                        }
                    }
                }
            }
        }

        if(weightdata !=null) {
            if (weightdata.size() > 0) {
                for (scaleInfo info : weightdata) {
                    if(info.getDetails()!=null){
                        if(info.getDetails().size()>0){
                            if (!info.getName().equals("Sample") && !excelist.contains(info.getName())) {
                                excelist.add(info.getName());
                                otherlist.add(info.getName());
                            }
                        }
                    }
                }
            }
        }

        if(timedistancedata !=null) {
            if (timedistancedata.size() > 0) {
                for (swimmingCyclingInfo info : timedistancedata) {
                    if(info!=null){
                        if(info.getDetails()!=null){
                            if(info.getDetails().size()>0){
                                if (!info.getName().equals("Sample") && !excelist.contains(info.getName())) {
                                    excelist.add(info.getName());
                                    otherlist.add(info.getName());
                                }
                            }
                        }
                    }
                }
            }
        }

        if(excelist.size()>0) {
            adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.textview, excelist);
            listview.setAdapter(adapter);
        }else{
            excelist.add("No charts available, add some records first");
            adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.textview, excelist);
            listview.setAdapter(adapter);
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!excelist.get(0).equals("No charts available, add some records first")){
                    if(otherlist.contains(excelist.get(i))){
                        Intent intent = new Intent(getActivity(), otherCharts.class);
                        intent.putExtra("excercise",excelist.get(i));
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getActivity(), Charts.class);
                        intent.putExtra("excercise",excelist.get(i));
                        startActivity(intent);
                    }

                }

            }
        });

        return view;

    }



}
