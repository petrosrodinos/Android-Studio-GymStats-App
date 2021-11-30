package com.example.gymstats.Activities;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gymstats.Adapters.adapterCycling;
import com.example.gymstats.Data.SwimmingCycling.swimmingCyclingInfo;
import com.example.gymstats.Data.SwimmingCycling.swimmingCyclingdetails;
import com.example.gymstats.Dialogs.addSwimmingDialog;
import com.example.gymstats.Dialogs.allSetsDelete;
import com.example.gymstats.Dialogs.deleteSetDialog;
import com.example.gymstats.Other.fileManager;
import com.example.gymstats.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class SwimmingCycling extends AppCompatActivity implements allSetsDelete.ExampleDialogListener,deleteSetDialog.ExampleDialogListener,addSwimmingDialog.ExampleDialogListener{
    private ArrayList<String> sets = new ArrayList<>();
    private List<swimmingCyclingdetails> detailslist = new ArrayList<>();
    List<swimmingCyclingInfo> jsondata = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listview;
    fileManager fm;
    Gson gson;
    Intent intent;
    swimmingCyclingInfo info=null;
    int infoindex=0,listindex,counter;
    String date;
    String finaltext;
    adapterCycling adapterCycling;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimming_cycling);

        adapter = new ArrayAdapter<>(this,R.layout.textview,sets);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_details);
        listview = findViewById(R.id.listview2);

        fm = new fileManager(this);

        gson = new Gson();

        String res = fm.readFromFile(6);
        Type workoutday = new TypeToken<ArrayList<swimmingCyclingInfo>>() {}.getType();
        jsondata = gson.fromJson(res, workoutday);
        intent = getIntent();
        finaltext = intent.getStringExtra("excercise");

        Objects.requireNonNull(getSupportActionBar()).setTitle(finaltext);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (swimmingCyclingInfo currinfo : jsondata) {
            if(currinfo != null){
                if (currinfo.getName() != null) {
                    if (!currinfo.getName().isEmpty()) {
                        if (currinfo.getName().equals(finaltext)) {
                            info = currinfo;
                            detailslist = info.getDetails();
                            infoindex = jsondata.indexOf(currinfo);
                            break;
                        }
                    }
                }
            }
        }

        if(detailslist!=null) {
            if(detailslist.size()>0){
                adapterCycling = new adapterCycling(this,detailslist);
                listview.setAdapter(adapterCycling);
            }else{
                sets.add("You have not added any record yet");
                listview.setAdapter(adapter);
            }
        }


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                if(sets.size()==0){
                    listindex = pos;
                    deleteSetDialog delete = new deleteSetDialog();
                    delete.show(getSupportFragmentManager(), "example dialog");
                }
                return true;
            }
        });
    }



    @Override
    public void addSwimming(float distance, float time) {
        if(sets.size()>=1){
            sets.clear();
        }
        addRecord(distance,time);
    }

    public void addRecord(float distance, float time){
        if(info==null){
            info = new swimmingCyclingInfo();
            info.setName(finaltext);
            info.setDetails(detailslist);
        }
        swimmingCyclingdetails details = new swimmingCyclingdetails(distance,time,stringDate());
        detailslist.add(details);
        info.setDetails(detailslist);
        updateData();
        if(adapterCycling==null){
            adapterCycling = new adapterCycling(this,detailslist);
            listview.setAdapter(adapterCycling);
        }
        adapterCycling.notifyDataSetChanged();
    }


    public void updateData(){
        if(jsondata.contains(info)){
            jsondata.set(infoindex,info);
        }else{
            jsondata.add(info);
        }
        String json = gson.toJson(jsondata);
        fm.writeToFile(6,json);
    }


    public void addSet(View view){
        addSwimmingDialog open = new addSwimmingDialog();
        open.show(getSupportFragmentManager(), "example dialog");
    }



    @Override
    public void deleteSet() {
        detailslist.remove(listindex);
        updateData();
        adapterCycling.notifyDataSetChanged();
        if(detailslist.size()==0){
            sets.add("You have not added record yet");
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deleteSets() {
        if(detailslist.size()>0){
            detailslist.clear();
            jsondata.remove(info);
            updateData();
            Toast.makeText(this,"Records Deleted",Toast.LENGTH_SHORT).show();
            sets.add("You have not added any record yet");
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapterCycling.notifyDataSetChanged();
        }else{
            Toast.makeText(this,"List is already empty",Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("SimpleDateFormat")
    public String stringDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd-MM-yyyy");
        String cdate = df.format(c.getTime());
        return cdate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }else if(item.getItemId() == R.id.deletee){
            allSetsDelete delete = new allSetsDelete();
            delete.show(getSupportFragmentManager(), "example dialog");
        }else if(item.getItemId() == R.id.database){
            Intent intent = new Intent(getApplicationContext(), jsonData.class);
            intent.putExtra("excercise",finaltext);
            intent.putExtra("number",6);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
