package com.example.gymstats;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gymstats.Data.Details;
import com.example.gymstats.Data.Info;
import com.example.gymstats.Dialogs.addSetDialog;
import com.example.gymstats.Dialogs.allSetsDelete;
import com.example.gymstats.Dialogs.deleteSetDialog;
import com.example.gymstats.Other.fileManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ExcerciseDetails extends AppCompatActivity implements addSetDialog.ExampleDialogListener, allSetsDelete.ExampleDialogListener,deleteSetDialog.ExampleDialogListener{
    private ArrayList<String> sets = new ArrayList<>();
    private List<Details> detailslist = new ArrayList<>();
    List<Info> jsondata = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listview;
    fileManager fm;
    Gson gson;
    Intent intent;
    Info info=null;
    int infoindex=0,listindex,counter;
    String askisi,date;
    private TextView noexce;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_details);
        listview = findViewById(R.id.listview2);
        noexce = findViewById(R.id.noexce);

        fm = new fileManager(this);
        gson = new Gson();

        String res = fm.readFromFile(2);
        Type workoutday = new TypeToken<ArrayList<Info>>() {}.getType();
        jsondata = gson.fromJson(res, workoutday);

        intent = getIntent();
        askisi = intent.getStringExtra("excercise");


        String finaltext = Objects.requireNonNull(askisi).substring(0,askisi.indexOf(":")).toUpperCase();

        if(askisi!=null || !askisi.isEmpty()){
            Objects.requireNonNull(getSupportActionBar()).setTitle(finaltext);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for(Info currinfo:jsondata){
            if(currinfo.getName()!=null) {
                if (!currinfo.getName().isEmpty()){
                    if (currinfo.getName().equals(finaltext)) {
                        info = currinfo;
                        infoindex = jsondata.indexOf(currinfo);
                        break;
                    }
                }
            }
        }

        if(info==null){
            info = new Info();
            info.setName(finaltext);
            info.setDetails(detailslist);
            updateData();
        }

        if(info.getDetails()!=null){
            detailslist = info.getDetails();
        }

        //fill listview
        fillView();


        //set listview
        if(sets!=null){
            adapter = new ArrayAdapter<>(this,R.layout.textview,sets);
            listview.setAdapter(adapter);
        }if(sets.size()<=0){
            noexce.setVisibility(View.VISIBLE);
        }


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
                listindex = pos;
                deleteSetDialog delete = new deleteSetDialog();
                delete.show(getSupportFragmentManager(), "example dialog");
//                listview.getChildAt(listindex).setBackgroundColor(
//                        Color.parseColor("#ccffcc"));
                return true;
            }
        });

    }


    public void fillView(){
        if(detailslist!=null){
            int i=0;
            for(Details det:detailslist) {
                if(i==0){
                    date = det.getDate();
                    sets.add("Date: " + det.getDate() + "\nSet: " + (++counter) + " Weight: " + det.getWeight() + " Reps: " + det.getReps());
                    i++;
                }else if(i>0 && det.getDate().equals(date)){
                    sets.add("Set: " + (++counter) + " Weight: " + det.getWeight() + " Reps: " + det.getReps());
                }else{
                    counter=0;
                    date = det.getDate();
                    sets.add("Date: " + det.getDate() + "\nSet: " + (++counter) + " Weight: " + det.getWeight() + " Reps: " + det.getReps());
                    i++;
                }
            }
        }
    }

    @Override
    public void applyTexts(float weight, int reps) {
        Details details = new Details(weight,reps);
        details.setDate(stringDate(2));
        detailslist.add(details);
        info.setDetails(detailslist);
        updateData();
        sets.add("Set: "+(++counter)+" Weight: "+weight+" Reps: "+reps);
        if(sets!=null){
            adapter.notifyDataSetChanged();
        }
        noexce.setVisibility(View.INVISIBLE);

    }

    public void updateData(){
        if(jsondata.contains(info)){
            jsondata.remove(infoindex);
            jsondata.add(infoindex,info);
        }else{
            jsondata.add(info);
        }

        String json = gson.toJson(jsondata);
        fm.writeToFile(2,json);
    }


    public void addSet(View view){
        addSetDialog open = new addSetDialog();
        open.show(getSupportFragmentManager(), "example dialog");
    }



    @Override
    public void deleteSet() {
        info.getDetails().remove(listindex);
        sets.remove(listindex);
        if(sets!=null){
            adapter.notifyDataSetChanged();
        }
        updateData();
    }

    @Override
    public void deleteSets() {
        if(info.getDetails().size()>0){
            info.getDetails().clear();
            updateData();
            Toast.makeText(this,"Excercises Deleted",Toast.LENGTH_SHORT).show();
            sets.clear();
            adapter.notifyDataSetChanged();
            noexce.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this,"Excercises List Already Empty",Toast.LENGTH_SHORT).show();
        }
    }

//    public String getDate(){
//        String cdate = stringDate(2);
//
//        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
//        String date = sharedPreferences.getString("date","00/00");
//
//        if(!date.equals(cdate)){
//            SharedPreferences sharedPreferences1 = getSharedPreferences("shared", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences1.edit();
//            editor.putString("date",cdate);
//            editor.apply();
//            return "ok";
//        }else{
//            return "not";
//        }
//    }

    @SuppressLint("SimpleDateFormat")
    public String stringDate(int i){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df;
        if(i==1){
            df = new SimpleDateFormat("dd-MM");
        }else{
            df = new SimpleDateFormat("dd-MM-yyyy");
        }

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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }


}