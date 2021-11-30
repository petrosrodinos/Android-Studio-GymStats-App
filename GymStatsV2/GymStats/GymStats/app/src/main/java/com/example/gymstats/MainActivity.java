package com.example.gymstats;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gymstats.Data.Details;
import com.example.gymstats.Data.Excercises;
import com.example.gymstats.Data.Info;
import com.example.gymstats.Data.Workout;
import com.example.gymstats.Fragments.ExcerciseListFragment;
import com.example.gymstats.Fragments.TrackFragment;
import com.example.gymstats.Fragments.homeFragment;
import com.example.gymstats.Other.fileManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static final String EXTRA_TEXT = "com.example.gymstats.EXTRA_TEXT";
    private long backPressedTime;
    private Toast backToast;
    fileManager fm;
    List<Excercises> excercises = new ArrayList<>();
    List<Details> details = new ArrayList<>();
    List<Info> infos = new ArrayList<>();
    List<Workout> workouts = new ArrayList<>();
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = new fileManager(this);

        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        boolean firstrun = sharedPreferences.getBoolean("firstrun", true);
        if(firstrun) {
            addPreferences();
        }

        BottomNavigationView bottom = findViewById(R.id.bottom_navigation);
        bottom.setSelectedItemId(R.id.gym);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new homeFragment()).commit();

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.charts:
                        selectedFragment = new ExcerciseListFragment();
                        break;
                    case R.id.track:
                        selectedFragment = new TrackFragment();
                        break;
                    case R.id.gym:
                        selectedFragment = new homeFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });

    }

    private void addPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstrun",false);
        editor.apply();
        addSampleData();
    }

    public void addSampleData(){
        fm.writeToFile(1,"");
        fm.writeToFile(2,"");
        Details detail = new Details(0,0,"00/00/0000");
        details.add(detail);
        infos.add(new Info("Sample",details));

        Excercises excer = new Excercises();
        excercises.add(excer);
        workouts.add(new Workout("monday","",excercises));
        workouts.add(new Workout("tuesday","",excercises));
        workouts.add(new Workout("wednesday","",excercises));
        workouts.add(new Workout("thursday","",excercises));
        workouts.add(new Workout("friday","",excercises));
        workouts.add(new Workout("saturday","",excercises));
        workouts.add(new Workout("sunday","",excercises));

        String workout = gson.toJson(workouts);
        String infostring = gson.toJson(infos);
        //Log.d("ggg",workout);
        //Log.d("ggg",infostring);
        fm.writeToFile(1,workout);
        fm.writeToFile(2,infostring);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


}

