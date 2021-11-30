package com.example.gymstats.Fragments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gymstats.Activities.caloriesCharts;
import com.example.gymstats.Adapters.adapterCalories;
import com.example.gymstats.Data.Calories.Calories;
import com.example.gymstats.Dialogs.addFood;
import com.example.gymstats.Dialogs.foodDelete;
import com.example.gymstats.Other.fileManager;
import com.example.gymstats.Other.sampleData;
import com.example.gymstats.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import static android.content.Context.MODE_PRIVATE;

public class caloriesFragment extends Fragment implements View.OnClickListener,addFood.OnInputSelected,foodDelete.OnInputSelected{
    private Button addfood;
    private Button showchart;
    private ListView listview;
    private List<String> calories = new ArrayList<>();
    private List<Calories> jsondata = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> adapter;
    private fileManager fm;
    private Gson gson;
    private int listindex,calories1,protein,carbs,fats;
    private Calories cal;
    private CountDownTimer mCountDownTimer;
    adapterCalories adapterCalories;
    private static final long START_TIME_IN_MILLIS = 86400000;
    //private static final long START_TIME_IN_MILLIS = 5000;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SimpleDateFormat")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_calories, container, false);
        addfood = view.findViewById(R.id.addfood);
        showchart = view.findViewById(R.id.showchart);
        listview = view.findViewById(R.id.listviewcal);
        addfood.setOnClickListener(this);

        adapter = new ArrayAdapter<>(getActivity(),R.layout.textview,calories);

        showchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchart();
            }
        });

        fm = new fileManager(getActivity());
        gson = new Gson();

        String res = fm.readFromFile(7);

        Type colorie = new TypeToken<ArrayList<Calories>>() {}.getType();
        if(!res.isEmpty()){
            jsondata = gson.fromJson(res, colorie);
        }

        if(jsondata!=null){
            if(jsondata.size()>0 && jsondata.get(0).getName()!=null){
                adapterCalories = new adapterCalories(getActivity(),jsondata);
                listview.setAdapter(adapterCalories);
            }else{
                calories.add("You have not added any record yet");
                listview.setAdapter(adapter);
            }
        }


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
                if(calories.size()==0){
                    listindex = pos;
                    openDelete();
                }
                return true;
            }
        });

        return view;
    }

    private void openDelete(){
        foodDelete dialog = new foodDelete();
        dialog.setTargetFragment(this, 1);
        assert getFragmentManager() != null;
        dialog.show(getFragmentManager(), "MyCustomDialog");
    }

    public void onClick(View v){
        addFood dialog = new addFood();
        dialog.setTargetFragment(this, 1);
        assert getFragmentManager() != null;
        dialog.show(getFragmentManager(), "MyCustomDialog");
    }

    private void openchart(){
        Intent intent = new Intent(this.getActivity(), caloriesCharts.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void sendInput(String name,String calories1,String protein,String carbs,String fats) {
        if(calories.size()>=1){
            calories.clear();
            addTime();
            Toast.makeText(getActivity(),"Data will be stored for 24 hours",Toast.LENGTH_LONG).show();
        }
        addData(name,calories1,protein,carbs,fats);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void addData(String name, String calories1, String protein, String carbs, String fats){
        if(calories1.isEmpty() && (!protein.isEmpty() && !carbs.isEmpty() && !fats.isEmpty())){
            int calor = (Integer.parseInt(protein)*4) + (Integer.parseInt(carbs)*4) + (Integer.parseInt(fats)*9);
            cal = new Calories(name.toUpperCase(),calor,Integer.parseInt(protein),Integer.parseInt(carbs),Integer.parseInt(fats));
        }else if(!calories1.isEmpty() && (protein.isEmpty() && carbs.isEmpty() && fats.isEmpty())){
            cal = new Calories(name.toUpperCase(),Integer.parseInt(calories1));
        }else if(!calories1.isEmpty() && (!protein.isEmpty() && !carbs.isEmpty() && !fats.isEmpty())){
            cal = new Calories(name.toUpperCase(),Integer.parseInt(calories1),Integer.parseInt(protein),Integer.parseInt(carbs),Integer.parseInt(fats));
        }
        updateData();
        if(adapterCalories==null){
            adapterCalories = new adapterCalories(getActivity().getApplicationContext(),jsondata);
        }
        listview.setAdapter(adapterCalories);
        adapterCalories.notifyDataSetChanged();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void deleteFood() {
        try{
            sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("caloriedata",MODE_PRIVATE);
            calories1 = sharedPreferences.getInt("calories", 0);
            protein = sharedPreferences.getInt("protein", 0);
            carbs = sharedPreferences.getInt("carbs", 0);
            fats = sharedPreferences.getInt("fats", 0);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("calories",calories1-jsondata.get(listindex).getCalories());
            editor.putInt("protein",protein-jsondata.get(listindex).getProtein());
            editor.putInt("carbs",carbs-jsondata.get(listindex).getProtein());
            editor.putInt("fats",fats-jsondata.get(listindex).getFats());
            editor.apply();

            jsondata.remove(jsondata.get(listindex));
            String json = gson.toJson(jsondata);
            fm.writeToFile(7,json);
            adapterCalories.notifyDataSetChanged();

            if(jsondata.size()==0){
                calories.add("You have not added any record yet");
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if(mCountDownTimer!=null){
                    if(mTimerRunning){
                        mTimerRunning = false;
                        mCountDownTimer.cancel();
                        mTimeLeftInMillis = START_TIME_IN_MILLIS;
                    }
                }
            }
        }catch(NullPointerException e){

        }

    }


    private void updateData(){
        sharedPreferences = this.getActivity().getSharedPreferences("caloriedata",MODE_PRIVATE);
        calories1 = sharedPreferences.getInt("calories", 0);
        protein = sharedPreferences.getInt("protein", 0);
        carbs = sharedPreferences.getInt("carbs", 0);
        fats = sharedPreferences.getInt("fats", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("calories",cal.getCalories()+calories1);
        editor.putInt("protein",cal.getProtein()+protein);
        editor.putInt("carbs",cal.getCarbs()+carbs);
        editor.putInt("fats",cal.getFats()+fats);
        editor.apply();

        if(jsondata.isEmpty()){
            jsondata.add(cal);
        }
        else if(jsondata.get(0).getFats()==0 && jsondata.get(0).getProtein()==0 && jsondata.get(0).getCarbs()==0 && jsondata.get(0).getCalories()==0){
            jsondata.set(0,cal);
        }else{
            jsondata.add(cal);
        }
        String json = gson.toJson(jsondata);
        fm.writeToFile(7,json);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void addTime(){
        //Log.d("ggg","started");
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        //Log.d("ggg","left: "+mTimeLeftInMillis);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;

//                int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
//                int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
//                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
//                Log.d("ggg",timeLeftFormatted);
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                //Log.d("ggg","finished");
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                clearData();
            }
        }.start();
        mTimerRunning = true;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                mTimerRunning = false;
                //Log.d("ggg","perasan");
                clearData();
            } else {
                addTime();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void clearData(){
        //Log.d("ggg","perasan");
        sampleData sample = new sampleData(getActivity());
        sample.restoreData(7);
        calories.clear();

        sharedPreferences = this.getActivity().getSharedPreferences("caloriedata",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences.edit();
        editor2.putInt("calories",0);
        editor2.putInt("protein",0);
        editor2.putInt("carbs",0);
        editor2.putInt("fats",0);
        editor2.apply();

        calories.add("You have not added any record yet");
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
