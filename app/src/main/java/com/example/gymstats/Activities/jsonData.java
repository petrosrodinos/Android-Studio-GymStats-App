package com.example.gymstats.Activities;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.example.gymstats.Other.fileManager;
import com.example.gymstats.Other.sampleData;
import com.example.gymstats.R;

import java.util.Objects;

public class jsonData extends AppCompatActivity {
    String day;
    EditText data;
    fileManager fm;
    int num;
    private ClipboardManager clipboard;
    private ClipData myClip;
    String res;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_data);
        data = findViewById(R.id.data);
        clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Intent intent = getIntent();
        day = intent.getStringExtra("excercise");
        num = intent.getIntExtra("number",0);
        Objects.requireNonNull(getSupportActionBar()).setTitle(day+" DATA");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm = new fileManager(this);
        res = fm.readFromFile(num);
        data.setText(res);


    }

    public void copydata(View view){
        myClip = ClipData.newPlainText("text", res);
        clipboard.setPrimaryClip(myClip);
        Toast.makeText(getApplicationContext(), "DATA COPIED",Toast.LENGTH_SHORT).show();
    }

    public void save(View view){
        fm.writeToFile(num,data.getText().toString());
        Toast.makeText(getApplicationContext(), "DATA SAVED",Toast.LENGTH_SHORT).show();
    }

    public void pastedata(View view){
        sampleData sample = new sampleData(this);
        sample.restoreData(num);
        data.setText(fm.readFromFile(num));
        Toast.makeText(getApplicationContext(), "DATA RESTORED",Toast.LENGTH_SHORT).show();
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
