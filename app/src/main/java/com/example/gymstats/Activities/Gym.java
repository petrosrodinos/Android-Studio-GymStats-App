package com.example.gymstats.Activities;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.gymstats.MainActivity;
import com.example.gymstats.R;

import java.util.Objects;

public class Gym extends AppCompatActivity implements View.OnClickListener{
    private CardView day1,day2,day3,day4,day5,day6,day7;
    public static final String EXTRA_TEXT = "com.example.gymstats.EXTRA_TEXT";
    String day;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        Intent intent = getIntent();
        day = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        Objects.requireNonNull(getSupportActionBar()).setTitle(day);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);
        day5 = findViewById(R.id.day5);
        day6 = findViewById(R.id.day6);
        day7 = findViewById(R.id.day7);
        day1.setOnClickListener(this);
        day2.setOnClickListener(this);
        day3.setOnClickListener(this);
        day4.setOnClickListener(this);
        day5.setOnClickListener(this);
        day6.setOnClickListener(this);
        day7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent i = new Intent(this, Day.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
