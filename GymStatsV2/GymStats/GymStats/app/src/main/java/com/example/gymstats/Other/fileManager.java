package com.example.gymstats.Other;
import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

public class fileManager {
    private String workouts = "workouts.json";
    private String infos = "infos.json";
    private String file;
    public Context ctx;

    public fileManager(Context ctx) {
        this.ctx = ctx;
    }

    public String readFromAsset(){
        String str;
        try{
            InputStream inputStream = ctx.getAssets().open("sample.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            str = new String(buffer,"UTF-8");

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return str;

    }

    public String readFromFile(int num) {
        if(num==1){
            file=workouts;
        }else{
            file=infos;
        }
        FileInputStream fis = null;
        try {
            fis = ctx.openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String res;
            while ((res = br.readLine()) != null) {
                sb.append(res).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void writeToFile(int num,String json) {
        if(num==1){
            file=workouts;
        }else{
            file=infos;
        }
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(file, MODE_PRIVATE);
            fos.write(json.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
