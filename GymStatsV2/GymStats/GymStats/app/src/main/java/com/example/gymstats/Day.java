package com.example.gymstats;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gymstats.Data.Excercises;
import com.example.gymstats.Data.Workout;
import com.example.gymstats.Dialogs.addExcerciseDialog;
import com.example.gymstats.Dialogs.allSetsDelete;
import com.example.gymstats.Dialogs.deleteExcerciseDialog;
import com.example.gymstats.Other.fileManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day extends AppCompatActivity implements addExcerciseDialog.ExampleDialogListener, deleteExcerciseDialog.ExampleDialogListener,allSetsDelete.ExampleDialogListener{
    public EditText name;
    private TextView noexce;
    private ListView listview;
    public String day;
    private final ArrayList<String> exc = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    fileManager fm;
    Gson gson;
    List<Workout> jsondata = new ArrayList<>();
    Workout currday;
    int index,listindex;
    List<Excercises> currexc;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        listview = findViewById(R.id.listview);
        name = findViewById(R.id.name);
        noexce = findViewById(R.id.noexce);

        Intent intent = getIntent();
        day = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        Objects.requireNonNull(getSupportActionBar()).setTitle(day);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm = new fileManager(this);
        gson = new Gson();

        String res = fm.readFromFile(1);
        Type workoutday = new TypeToken<ArrayList<Workout>>() {}.getType();
        jsondata = gson.fromJson(res, workoutday);

        if(jsondata!=null){
            for (Workout exc : jsondata) {
                if (exc.getDay().equalsIgnoreCase(day)) {
                    currday = exc;
                    name.setText(currday.getName(), TextView.BufferType.EDITABLE);
                    index = jsondata.indexOf(currday);
                    break;
                }
            }if(currday==null){
                currday = new Workout();
                jsondata.add(currday);
                index = jsondata.indexOf(currday);
            }
        }

        currexc = currday.getExcercise();
        if(currexc!=null) {
            for (Excercises ex : currexc) {
                if(ex.getName()!=null){
                    exc.add(ex.getName() + ":   " + ex.getSets() + "χ" + ex.getReps());
                }
            }
        }


        if(exc.size()>0){
            adapter = new ArrayAdapter<>(this,R.layout.textview,exc);
            listview.setAdapter(adapter);
        }else if(exc.size()<=0){
            noexce.setVisibility(View.VISIBLE);
        }




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(),ExcerciseDetails.class);
                    intent.putExtra("excercise",exc.get(i));
                    listindex = i;
                    startActivity(intent);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
                listindex = pos;
                deleteExcerciseDialog delete = new deleteExcerciseDialog();
                delete.show(getSupportFragmentManager(), "example dialog");

                return true;
            }
        });

    }


    public void setDayName(View view){
        //currday.setDay(day);
        if(name.getText().toString().equals("deleteall")){
            //addSampleData();
            Toast.makeText(getApplicationContext(),"DELETED",Toast.LENGTH_SHORT).show();
        }
        if(!name.getText().toString().isEmpty() && !name.getText().toString().equals("deleteall")){
            currday.setName(name.getText().toString().toUpperCase());
            name.setText(currday.getName(), TextView.BufferType.EDITABLE);
            updateData();
            Toast.makeText(getApplicationContext(),"Name Added",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Empty Name",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void applyTexts(String name1, String sets1, String reps1) {
        if(currday.getDay()==null || currday.getDay().isEmpty() || name.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Give a workout name",Toast.LENGTH_SHORT).show();
        }else {
                if (currexc != null) {
                    if(!nameExists(name1.toLowerCase())){
                        Excercises excercise = new Excercises();
                        excercise.setName(name1.toLowerCase());
                        excercise.setSets(Integer.parseInt(sets1));
                        excercise.setReps(Integer.parseInt(reps1));
                        currexc.add(excercise);
                        currday.setExcercise(currexc);
                        exc.add(excercise.getName() + ":   " + excercise.getSets() + "χ" + excercise.getReps());
                        updateData();
                        if(adapter==null){
                            adapter = new ArrayAdapter<>(this,R.layout.textview,exc);
                            adapter.notifyDataSetChanged();
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                        Toast.makeText(getApplicationContext(), "Excercise Added", Toast.LENGTH_SHORT).show();
                        noexce.setVisibility(View.INVISIBLE);
                    }else {
                        Toast.makeText(getApplicationContext(), "Excercise already exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error adding excercise", Toast.LENGTH_SHORT).show();
                }
//            }
        }
    }

    public boolean nameExists(String name){
        if(currexc!=null){
            for(Excercises ex:currexc){
                if(ex.getName()!=null) {
                    if(!ex.getName().isEmpty()){
                        if (ex.getName().equalsIgnoreCase(name)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void deleteExcercise() {
        currday.getExcercise().remove(listindex);
        exc.remove(listindex);
        adapter.notifyDataSetChanged();
        updateData();

    }

    public void updateData(){
        if(jsondata.contains(currday)){
            jsondata.remove(index);
            jsondata.add(index,currday);
        }else{
            jsondata.add(currday);
        }
        String json = gson.toJson(jsondata);
        fm.writeToFile(1,json);
    }

    public void addExcercise(View view){
        addExcerciseDialog exampleDialog = new addExcerciseDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void deleteSets() {
        if(currday.getExcercise().size()>0){
            currday.getExcercise().clear();
            updateData();
            Toast.makeText(this,"Excercises Deleted",Toast.LENGTH_SHORT).show();
            exc.clear();
            adapter.notifyDataSetChanged();
            noexce.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this,"Excercises list already empty",Toast.LENGTH_SHORT).show();
        }
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
