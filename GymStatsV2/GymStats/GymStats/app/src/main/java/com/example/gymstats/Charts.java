package com.example.gymstats;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.gymstats.Fragments.RepsFragment;
import com.example.gymstats.Fragments.SetsFragment;
import com.example.gymstats.Fragments.VolumeFragment;
import com.example.gymstats.Fragments.WeightFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;

public class Charts extends AppCompatActivity {
    String day;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        Intent intent = getIntent();
        day = intent.getStringExtra("excercise");
        Objects.requireNonNull(getSupportActionBar()).setTitle(day);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottom = findViewById(R.id.bottom_navigation);
        bottom.setSelectedItemId(R.id.weight);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new WeightFragment().newInstance(day)).commit();

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.reps:
                        selectedFragment = new RepsFragment().newInstance(day);
                        break;
                    case R.id.sets:
                        selectedFragment = new SetsFragment().newInstance(day);;
                        break;
                    case R.id.volume:
                        selectedFragment = new VolumeFragment().newInstance(day);;
                        break;
                    case R.id.weight:
                        selectedFragment = new WeightFragment().newInstance(day);;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();




                return true;
            }
        });


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
