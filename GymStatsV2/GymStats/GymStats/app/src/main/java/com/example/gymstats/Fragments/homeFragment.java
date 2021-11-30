package com.example.gymstats.Fragments;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymstats.Day;
import com.example.gymstats.R;

import androidx.annotation.Nullable;

public class homeFragment extends Fragment implements View.OnClickListener{
    private CardView day1,day2,day3,day4,day5,day6,day7;
    public static final String EXTRA_TEXT = "com.example.gymstats.EXTRA_TEXT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        day1 = view.findViewById(R.id.day1);
        day2 = view.findViewById(R.id.day2);
        day3 = view.findViewById(R.id.day3);
        day4 = view.findViewById(R.id.day4);
        day5 = view.findViewById(R.id.day5);
        day6 = view.findViewById(R.id.day6);
        day7 = view.findViewById(R.id.day7);
        day1.setOnClickListener(this);
        day2.setOnClickListener(this);
        day3.setOnClickListener(this);
        day4.setOnClickListener(this);
        day5.setOnClickListener(this);
        day6.setOnClickListener(this);
        day7.setOnClickListener(this);

        return view;


    }

    @Override
    public void onClick(View v){
        Intent i = new Intent(getActivity(), Day.class);
        if(v.getId()==R.id.day1){
            i.putExtra(EXTRA_TEXT, "MONDAY");
        }else if(v.getId()==R.id.day2){
            i.putExtra(EXTRA_TEXT, "TUESDAY");
        }else if(v.getId()==R.id.day3){
            i.putExtra(EXTRA_TEXT, "WEDNESDAY");
        }
        else if(v.getId()==R.id.day4){
            i.putExtra(EXTRA_TEXT, "THURSDAY");
        }
        else if(v.getId()==R.id.day5){
            i.putExtra(EXTRA_TEXT, "FRIDAY");
        }
        else if(v.getId()==R.id.day6){
            i.putExtra(EXTRA_TEXT, "SATURDAY");
        }
        else if(v.getId()==R.id.day7){
            i.putExtra(EXTRA_TEXT, "SUNDAY");
        }
        startActivity(i);
    }

}
