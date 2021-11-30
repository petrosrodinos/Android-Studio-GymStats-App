package com.example.gymstats.Activities;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.gymstats.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Running extends AppCompatActivity{
    private ListView listview;
    List<String> lista = new ArrayList<>();
    String day;
    private ArrayAdapter<String> adapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        Intent intent = getIntent();
        day = intent.getStringExtra("excercise");
        Objects.requireNonNull(getSupportActionBar()).setTitle(day);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listview = findViewById(R.id.listview4);

        if(day.equals("HARDLE RACE")){
            lista.add("100 Meters ");
            lista.add("110 Meters ");
            lista.add("400 Meters ");
        }else{
            lista.add("100 Meters");
            lista.add("200 Meters");
            lista.add("400 Meters");
            lista.add("800 Meters");
            lista.add("1500 Meters");
            lista.add("5000 Meters");
            lista.add("10000 Meters");
        }

        adapter = new ArrayAdapter<>(this,R.layout.textview,lista);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Time.class);
                intent.putExtra("excercise",lista.get(i));
                startActivity(intent);
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
