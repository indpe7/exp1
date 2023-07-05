package com.example.shiyan3;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText select;
    boolean read;
    SharedPreferences bufferShared,likeShared,cities;
    Button selectButton,clearBuffer;
    ListView likeList;
    TextView result;
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
            String str = province+" "+city+" "+weather+" "+temperature+" "+winddirection+" "+windpower+" "+humidity+" "+reporttime;

            SharedPreferences.Editor editor = bufferShared.edit();
            editor.putString(city,str);
            editor.apply();

            Weather weather1 = new Weather(province,city,weather,temperature,winddirection,windpower,humidity,reporttime);
            Intent intent = new Intent(MainActivity.this,Weather_Activity.class);
            intent.putExtra("weather",weather1);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this,"查询的地点不存在",Toast.LENGTH_SHORT).show();
            Log.d("dht","没有找到");
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        read = false;
        //获取缓冲区
        bufferShared = getSharedPreferences("buffer",MODE_PRIVATE);
        //获取关注列表
        likeShared = getSharedPreferences("like",MODE_PRIVATE);
        cities = getSharedPreferences("city", Context.MODE_PRIVATE);

        likeList = (ListView) findViewById(R.id.likeList);
        select = (EditText) findViewById(R.id.select);
        selectButton = (Button) findViewById(R.id.selectButton);
        clearBuffer = (Button) findViewById(R.id.clearBuffer);
        result = (TextView) findViewById(R.id.result);
        
        List<String> citys = new ArrayList<>();
        Map<String, ?> allEntries = likeShared.getAll();
        for (Map.Entry<String,?> entry : allEntries.entrySet()){
            String key =entry.getKey();
            citys.add(key);
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,citys);
        likeList.setAdapter(adapter);


        if (read) {
            SharedPreferences.Editor editor = cities.edit();
            editor.clear();
            editor.apply();
            ReadUtils readUtils = new ReadUtils(MainActivity.this);
            readUtils.read();
        }
        likeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cti = citys.get(i);
                getWeatherOfCity(cti);
            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LIstActivity.class);
                startActivity(intent);
            }
        });
        clearBuffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = bufferShared.edit();
                editor.clear();
                editor.apply();
            }
        });
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = select.getText().toString();
                if (!bufferShared.getString(city,"").equals("")){
                    String str = bufferShared.getString(city,"");
                    String[] weatherStr = str.split(" ");
                    Weather weather = new Weather(weatherStr[0],weatherStr[1],weatherStr[2],weatherStr[3],weatherStr[4],weatherStr[5],weatherStr[6],weatherStr[7]);
                    Log.d("dht","通过缓存");
                    Intent intent = new Intent(MainActivity.this,Weather_Activity.class);
                    intent.putExtra("weather",weather);
                    startActivity(intent);
                }
                else {
                    getWeatherOfCity(city);
                }
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