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
import com.example.gymstats.Adapters.adapterDetails;
import com.example.gymstats.Data.Gym.Details;
import com.example.gymstats.Data.Gym.Info;
import com.example.gymstats.Dialogs.addSetDialog;
import com.example.gymstats.Dialogs.allSetsDelete;
import com.example.gymstats.Dialogs.deleteSetDialog;
import com.example.gymstats.Dialogs.timerDialog;
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
    String date;
    String finaltext;
    adapterDetails adapterDetails;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_details);
        listview = findViewById(R.id.listview2);
        adapter = new ArrayAdapter<>(this,R.layout.textview,sets);
        fm = new fileManager(this);

        gson = new Gson();

        String res = fm.readFromFile(2);

        Type workoutday = new TypeToken<ArrayList<Info>>() {}.getType();
        jsondata = gson.fromJson(res, workoutday);

        intent = getIntent();
        finaltext = intent.getStringExtra("excercise");

        Objects.requireNonNull(getSupportActionBar()).setTitle(finaltext);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(jsondata!=null){
            for(Info currinfo:jsondata){
                if(currinfo.getName()!=null) {
                    if (!currinfo.getName().isEmpty()){
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
                adapterDetails = new adapterDetails(this,detailslist);
                listview.setAdapter(adapterDetails);
            }else{
                sets.add("You have not added any set yet");
                listview.setAdapter(adapter);
            }
        }



        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
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
    public void applyTexts(float weight, int reps,String notes) {
        if(sets.size()>=1){
            sets.clear();
        }
        addData(weight,reps,notes);
    }

    public void addData(float weight, int reps,String notes){
        if(info==null){
            info = new Info();
            info.setName(finaltext);
            info.setDetails(detailslist);
        }
        Details details = new Details(weight,reps,stringDate(),notes);
        detailslist.add(details);
        info.setDetails(detailslist);
        updateData();
        if(adapterDetails==null){
            adapterDetails = new adapterDetails(this,detailslist);
        }
        listview.setAdapter(adapterDetails);
        adapterDetails.notifyDataSetChanged();
    }

    public void updateData(){
        if(jsondata.contains(info)){
            jsondata.set(infoindex,info);
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
        detailslist.remove(listindex);
        updateData();
        adapterDetails.notifyDataSetChanged();
        if(detailslist.size()==0){
            sets.add("You have not added any set yet");
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
            sets.add("You have not added any set yet");
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapterDetails.notifyDataSetChanged();
        }else{
            Toast.makeText(this,"List is already empty",Toast.LENGTH_SHORT).show();
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
        }else if(item.getItemId() == R.id.timer){
            timerDialog timer = new timerDialog();
            timer.show(getSupportFragmentManager(),"timerdialog");
        }else if(item.getItemId() == R.id.database){
            Intent intent = new Intent(getApplicationContext(), jsonData.class);
            intent.putExtra("excercise","DETAILS");
            intent.putExtra("number",2);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailsmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }



}