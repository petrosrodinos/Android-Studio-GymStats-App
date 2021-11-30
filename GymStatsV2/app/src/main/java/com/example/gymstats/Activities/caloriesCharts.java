package com.example.gymstats.Activities;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.gymstats.Data.Calories.CalorieData;
import com.example.gymstats.Dialogs.setGoals;
import com.example.gymstats.Other.fileManager;
import com.example.gymstats.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class caloriesCharts extends AppCompatActivity implements setGoals.ExampleDialogListener{
    fileManager fm;
    Gson gson;
    List<CalorieData> jsondata = new ArrayList<>();
    List<ILineDataSet> datasets1 = new ArrayList<>();
    List<ILineDataSet> datasets2 = new ArrayList<>();
    List<ILineDataSet> datasets3 = new ArrayList<>();
    List<ILineDataSet> datasets4 = new ArrayList<>();
    ArrayList<PieEntry> piedata1  = new ArrayList<>();
    ArrayList<PieEntry> piedata2  = new ArrayList<>();
    ArrayList<PieEntry> piedata3  = new ArrayList<>();
    ArrayList<PieEntry> piedata4  = new ArrayList<>();
    TextView calories,protein,carbs,fats,textview;
    private SharedPreferences sharedPreferences;
    LineChart line1,line2,line3,line4;
    PieChart pie1,pie2,pie3,pie4;
    List<Entry> data1 = new ArrayList<>();
    List<Entry> data2 = new ArrayList<>();
    List<Entry> data3 = new ArrayList<>();
    List<Entry> data4 = new ArrayList<>();
    int a,b,c,d,e,calories2,protein2,carbs2,fats2,caloriesgoal,proteingoal,carbsgoal,fatsgoal;

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_charts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Calorie Charts");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        calories = findViewById(R.id.calories);
        protein = findViewById(R.id.protein);
        carbs = findViewById(R.id.carbs);
        fats = findViewById(R.id.fats);
        line1 = findViewById(R.id.line_chart1);
        line2 = findViewById(R.id.line_chart2);
        line3 = findViewById(R.id.line_chart3);
        line4 = findViewById(R.id.line_chart4);
        pie1 = findViewById(R.id.pie1);
        pie2 = findViewById(R.id.pie2);
        pie3 = findViewById(R.id.pie3);
        pie4 = findViewById(R.id.pie4);
        textview = findViewById(R.id.textView10);

        sharedPreferences = this.getSharedPreferences("caloriedata",MODE_PRIVATE);
        calories2 = sharedPreferences.getInt("calories", 0);
        protein2 = sharedPreferences.getInt("protein", 0);
        carbs2 = sharedPreferences.getInt("carbs", 0);
        fats2 = sharedPreferences.getInt("fats", 0);

        sharedPreferences = this.getSharedPreferences("goals",MODE_PRIVATE);
        caloriesgoal = sharedPreferences.getInt("caloriegoal", 0);
        proteingoal = sharedPreferences.getInt("proteingoal", 0);
        carbsgoal = sharedPreferences.getInt("carbsgoal", 0);
        fatsgoal = sharedPreferences.getInt("fatsgoal", 0);

        calories.setText("Calories: "+calories2+"/"+caloriesgoal);
        protein.setText("Protein: "+protein2+"/"+proteingoal);
        carbs.setText("Carbs: "+carbs2+"/"+carbsgoal);
        fats.setText("Fats: "+fats2+"/"+fatsgoal);

        fm = new fileManager(this);
        gson = new Gson();

        fullList();
        setCyrcles();
        setGraphs();


    }

    private void fullList(){
        String res = fm.readFromFile(8);
        Type caloriedata = new TypeToken<ArrayList<CalorieData>>() {}.getType();
        jsondata = gson.fromJson(res, caloriedata);
        data1.clear();
        data2.clear();
        data3.clear();
        data4.clear();
        if(jsondata!=null){
            if(!jsondata.isEmpty()){
                for(CalorieData cal : jsondata){
                    if(cal.getCalories()!=0){
                        data1.add(new Entry(++a,cal.getCalories()));
                        data2.add(new Entry(++b,cal.getProtein()));
                        data3.add(new Entry(++c,cal.getCarbs()));
                        data4.add(new Entry(++d,cal.getFats()));
                    }

                }
            }
        }
    }

    private void setGraphs(){
        datasets1.clear();
        datasets2.clear();
        datasets3.clear();
        datasets4.clear();
        Description description1 = new Description();
        description1.setText("Line Chart");
        description1.setTextSize(10);

        if(!data1.isEmpty()){
            LineDataSet dataset = new LineDataSet(data1,"Calories");
            dataset.setColor(Color.RED);
            datasets1.add(dataset);

            LineData dat1 = new LineData(datasets1);
            line1.setData(dat1);
            line1.setDescription(description1);
            line1.setDrawGridBackground(true);
            line1.animateXY(1000,1000);
            line1.invalidate();

        }else{
            line1.setVisibility(View.GONE);
            e++;
        }


        if(!data2.isEmpty()){
            LineDataSet dataset2 = new LineDataSet(data2,"Protein");
            dataset2.setColor(Color.RED);
            datasets2.add(dataset2);

            LineData dat2= new LineData(datasets2);
            line2.setData(dat2);
            line2.setDescription(description1);
            line2.setDrawGridBackground(true);
            line2.animateXY(1000,1000);
            line2.invalidate();
        }else{
            line2.setVisibility(View.GONE);
            e++;
        }

        if(!data3.isEmpty()){
            LineDataSet dataset3 = new LineDataSet(data3,"Carbs");
            dataset3.setColor(Color.RED);
            datasets3.add(dataset3);

            LineData dat3 = new LineData(datasets3);
            line3.setData(dat3);
            line3.setDescription(description1);
            line3.setDrawGridBackground(true);
            line3.animateXY(1000,1000);
            line3.invalidate();
        }else{
            line3.setVisibility(View.GONE);
            e++;
        }

        if(!data4.isEmpty()){
            LineDataSet dataset4 = new LineDataSet(data4,"Fats");
            dataset4.setColor(Color.RED);
            datasets4.add(dataset4);

            LineData dat4 = new LineData(datasets4);
            line4.setData(dat4);
            line4.setDescription(description1);
            line4.setDrawGridBackground(true);
            line4.animateXY(1000,1000);
            line4.invalidate();
        }else{
            line4.setVisibility(View.GONE);
            e++;
        }

        if(e==4){
            textview.setText("No charts available for your goals");
        }
    }

    private void setCyrcles(){
        piedata1.clear();
        piedata2.clear();
        piedata3.clear();
        piedata4.clear();
        if(calories2!=0 && caloriesgoal!=0){
            piedata1.add(new PieEntry(calories2,"Consumed"));
            piedata1.add(new PieEntry(caloriesgoal,"Goal"));

            PieDataSet pieDataSet1 = new PieDataSet(piedata1,"");
            pieDataSet1.setValueTextColor(Color.BLACK);
            pieDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet1.setValueTextSize(16f);
            PieData pieData1 = new PieData(pieDataSet1);
            pie1.getDescription().setEnabled(false);
            pie1.setData(pieData1);
            pie1.setCenterText("Calories");
            pie1.animateXY(1000,1000);
            pie1.invalidate();
        }else{
            pie1.setVisibility(View.GONE);
        }

        if(protein2!=0 && proteingoal!=0){
            piedata2.add(new PieEntry(protein2,"Consumed"));
            piedata2.add(new PieEntry(proteingoal,"Goal"));

            PieDataSet pieDataSet2 = new PieDataSet(piedata2,"");
            pieDataSet2.setValueTextColor(Color.BLACK);
            pieDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet2.setValueTextSize(16f);
            PieData pieData2 = new PieData(pieDataSet2);
            pie2.getDescription().setEnabled(false);
            pie2.setData(pieData2);
            pie2.setCenterText("Protein");
            pie2.animateXY(1000,1000);
            pie2.invalidate();
        }else{
            pie2.setVisibility(View.GONE);
        }


        if(carbs2!=0 && carbsgoal!=0){
            piedata3.add(new PieEntry(carbs2,"Consumed"));
            piedata3.add(new PieEntry(carbsgoal,"Goal"));

            PieDataSet pieDataSet3 = new PieDataSet(piedata3,"");
            pieDataSet3.setValueTextColor(Color.BLACK);
            pieDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet3.setValueTextSize(16f);
            PieData pieData3 = new PieData(pieDataSet3);
            pie3.getDescription().setEnabled(false);
            pie3.setData(pieData3);
            pie3.setCenterText("Carbs");
            pie3.animateXY(1000,1000);
            pie3.invalidate();
        }else{
            pie3.setVisibility(View.GONE);
        }


        if(fats2!=0 && fatsgoal!=0){
            piedata4.add(new PieEntry(fats2,"Consumed"));
            piedata4.add(new PieEntry(fatsgoal,"Goal"));

            PieDataSet pieDataSet4 = new PieDataSet(piedata4,"");
            pieDataSet4.setValueTextColor(Color.BLACK);
            pieDataSet4.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet4.setValueTextSize(16f);
            PieData pieData4 = new PieData(pieDataSet4);
            pie4.getDescription().setEnabled(false);
            pie4.setData(pieData4);
            pie4.setCenterText("Fats");
            pie4.animateXY(1000,1000);
            pie4.invalidate();
        }else{
            pie4.setVisibility(View.GONE);
        }
    }


    public void setGoal(View view){
        setGoals exampleDialog = new setGoals();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setGoals(int calories1, int protein1, int carbs1, int fats1) {
        sharedPreferences = this.getSharedPreferences("goals",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("caloriegoal",calories1);
        editor.putInt("proteingoal",protein1);
        editor.putInt("carbsgoal",carbs1);
        editor.putInt("fatsgoal",fats1);
        editor.apply();

        caloriesgoal = calories1;
        proteingoal = protein1;
        carbsgoal = carbs1;
        fatsgoal = fats1;

        calories.setText("Calories: "+calories2+"/"+calories1);
        protein.setText("Protein: "+protein2+"/"+protein1);
        carbs.setText("Carbs: "+carbs2+"/"+carbs1);
        fats.setText("Fats: "+fats2+"/"+fats1);

        jsondata.add(new CalorieData(calories1,protein1,carbs1,fats1));
        String res = gson.toJson(jsondata);
        fm.writeToFile(8,res);
        fullList();
        setGraphs();
        setCyrcles();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return super.onOptionsItemSelected(item);
    }


}
