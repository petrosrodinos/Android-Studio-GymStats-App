package com.example.gymstats.Fragments;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gymstats.Activities.Gym;
import com.example.gymstats.Activities.Running;
import com.example.gymstats.Activities.Scale;
import com.example.gymstats.Activities.SwimmingCycling;
import com.example.gymstats.Activities.Distance;
import com.example.gymstats.R;

import androidx.annotation.Nullable;

public class homeFragment extends Fragment implements View.OnClickListener{
    private CardView gym,cycling,swiming,speed,polevault,longjump,empodia,sfaira,akontio,scale;
    public static final String EXTRA_TEXT = "com.example.gymstats.EXTRA_TEXT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        gym = view.findViewById(R.id.gym);
        cycling = view.findViewById(R.id.cycling);
        swiming = view.findViewById(R.id.swiming);
        speed = view.findViewById(R.id.speed);
        polevault = view.findViewById(R.id.polevault);
        longjump = view.findViewById(R.id.longjump);
        empodia = view.findViewById(R.id.empodia);
        sfaira = view.findViewById(R.id.javelin);
        akontio = view.findViewById(R.id.shotput);
        scale = view.findViewById(R.id.scale);

        gym.setOnClickListener(this);
        cycling.setOnClickListener(this);
        swiming.setOnClickListener(this);
        speed.setOnClickListener(this);
        polevault.setOnClickListener(this);
        longjump.setOnClickListener(this);
        empodia.setOnClickListener(this);
        sfaira.setOnClickListener(this);
        akontio.setOnClickListener(this);
        scale.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.gym){
            Intent i = new Intent(getActivity(), Gym.class);
            i.putExtra(EXTRA_TEXT, "GYM");
            startActivity(i);
        }
        else if(v.getId()==R.id.cycling){
            Intent i = new Intent(getActivity(), SwimmingCycling.class);
            i.putExtra("excercise", "CYCLING");
            startActivity(i);
        }else if(v.getId()==R.id.swiming){
            Intent i = new Intent(getActivity(), SwimmingCycling.class);
            i.putExtra("excercise", "SWIMMING");
            startActivity(i);
        }
        else if(v.getId()==R.id.speed){
            Intent i = new Intent(getActivity(), Running.class);
            i.putExtra("excercise", "RUNNING");
            startActivity(i);
        }
        else if(v.getId()==R.id.empodia){
            Intent i = new Intent(getActivity(), Running.class);
            i.putExtra("excercise", "HARDLE RACE");
            startActivity(i);
        }
        else if(v.getId()==R.id.longjump){
            Intent i = new Intent(getActivity(), Distance.class);
            i.putExtra("excercise", "LONG JUMP");
            startActivity(i);
        }
        else if(v.getId()==R.id.polevault){
            Intent i = new Intent(getActivity(), Distance.class);
            i.putExtra("excercise", "POLE VAULT");
            startActivity(i);
        }
        else if(v.getId()==R.id.javelin){
            Intent i = new Intent(getActivity(), Distance.class);
            i.putExtra("excercise", "JAVELIN");
            startActivity(i);
        }
        else if(v.getId()==R.id.shotput){
            Intent i = new Intent(getActivity(), Distance.class);
            i.putExtra("excercise", "SHOT PUT");
            startActivity(i);
        }
        else if(v.getId()==R.id.scale){
            Intent i = new Intent(getActivity(), Scale.class);
            i.putExtra("excercise", "SCALE");
            startActivity(i);
        }

    }

}
