package com.example.shiyan3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.poi.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CityActivity extends AppCompatActivity {
    String province;
    SharedPreferences cities;
    Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0 ){
                String weather = (String) msg.obj;
                Log.d("dht", "---主线程收到数据----" + weather);
                show(weather);
            }

        }
    };
    public void show(String TWeather){
        String regex = "\"province\":\"(.*?)\",\"city\":\"(.*?)\",\"adcode\":\"(.*?)\",\"weather\":\"(.*?)\",\"temperature\":\"(.*?)\",\"winddirection\":\"(.*?)\",\"windpower\":\"(.*?)\",\"humidity\":\"(.*?)\",\"reporttime\":\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(TWeather);

        if (matcher.find()) {
            String province = matcher.group(1);
            String city = matcher.group(2);
            String weather = matcher.group(4);
            String temperature = matcher.group(5);
            String winddirection = matcher.group(6);
            String windpower = matcher.group(7);
            String humidity = matcher.group(8);
            String reporttime = matcher.group(9);

            Log.d("dht", "---省份----" + province);
            Log.d("dht", "---城市----" + city);
            Log.d("dht", "---天气----" + weather);
            Log.d("dht", "---温度----" + temperature);
            Log.d("dht", "---风向----" + winddirection);
            Log.d("dht", "---风力----" + windpower);
            Log.d("dht", "---湿度----" + humidity);
            Log.d("dht", "---报告时间----" + reporttime);

            Weather weather1 = new Weather(province,city,weather,temperature,winddirection,windpower,humidity,reporttime);
            Intent intent = new Intent(CityActivity.this,Weather_Activity.class);
            intent.putExtra("weather",weather1);
            startActivity(intent);
        }
        else {
            Toast.makeText(CityActivity.this,"查询的地点不存在",Toast.LENGTH_SHORT).show();
            Log.d("dht","没有找到");
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        province = getIntent().getStringExtra("province");
        cities = getSharedPreferences("city",MODE_PRIVATE);
        String str = cities.getString(province,"");
        String[] a = str.split(" ");
        List<String> citys = new ArrayList<>();
        Collections.addAll(citys, a);
        ListView city = (ListView) findViewById(R.id.CCity);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,citys);
        city.setAdapter(adapter);
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String city = citys.get(i);
                getWeatherOfCity(city);
            }
        });

    }
    private void getWeatherOfCity(String selectedCity) {
        // 开启子线程，请求网络
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 请求网络
                String weatherOfCity = NetworkTask.getWeatherOfCity(selectedCity);
                // 使用handler将数据传递给主线程
                Message message = Message.obtain();
                message.what = 0;
                message.obj = weatherOfCity;
                mHandler.sendMessage(message);
            }
        }).start();

    }
}