package com.example.shiyan3;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.RequiresPermission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ReadUtils {
    private Context context;
    SharedPreferences cities;
    public ReadUtils(Context context){
        this.context = context;
    }
    public void read(){
        cities = context.getSharedPreferences("city",Context.MODE_PRIVATE);
        try {
            InputStream inputStream = context.getAssets().open("city.txt");

            // 将输入流转换为字符串

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            SharedPreferences.Editor editor = cities.edit();
            String province = "";
            String province_number = "";
            StringBuilder ci= new StringBuilder();
            while ((line = reader.readLine()) != null) {
//                Log.d("dht","读取的文件"+line);
                String[] str = line.split("\t");
//                Log.d("dht","城市:"+str[0]);
//                Log.d("dht","编号:"+str[1]);
//                Log.d("dht","city_code"+str[2]);
                String city = str[0];
                String number = str[1];
                String cityCode = str[2];



                if (!cityCode.equals("0")){
                    ci.append(city).append(" ");
                }
                else{
                    editor.putString(province, ci.toString());
                    Log.d("dht","省:"+province);
                    Log.d("dht","市:"+ ci);
                    province_number = cityCode;
                    province = city;
                    ci = new StringBuilder();
                    ci.append(province).append(" ");
                }
//                Log.d("dht","省编号:"+city_number);
            }
            editor.apply();


            // 处理文件内容
            // ...

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
