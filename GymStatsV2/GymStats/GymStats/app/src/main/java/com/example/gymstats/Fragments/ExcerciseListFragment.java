package com.example.gymstats.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gymstats.Charts;
import com.example.gymstats.Data.Info;
import com.example.gymstats.Other.fileManager;
import com.example.gymstats.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ExcerciseListFragment extends Fragment {
    private ListView listview;
    List<Info> jsondata = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    List<String> excelist = new ArrayList<>();
    fileManager fm;
    Gson gson;
    private TextView noexce;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_excercise_list, container, false);
        listview = view.findViewById(R.id.listview3);
        fm = new fileManager(getActivity().getApplicationContext());
        gson = new Gson();
        noexce = view.findViewById(R.id.noexce);

        String res = fm.readFromFile(2);
        Type workoutday = new TypeToken<ArrayList<Info>>() {}.getType();
        jsondata = gson.fromJson(res, workoutday);

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


        if(excelist.size()>0) {
            adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.textview, excelist);
            listview.setAdapter(adapter);
        }else{
            noexce.setVisibility(View.VISIBLE);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Charts.class);
                intent.putExtra("excercise",excelist.get(i));
                startActivity(intent);
            }
        });



        return view;

    }




}
