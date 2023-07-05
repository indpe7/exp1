package com.example.shiyan3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weather_Activity extends AppCompatActivity {
    Weather weather;
    TextView sp_city,tv_tem,tv_weather,tv_tem_low_high,tv_win,tv_air,like,back;
    ImageView iv_weather;
    SharedPreferences likeShared;
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
            Intent intent = new Intent(Weather_Activity.this,Weather_Activity.class);
            intent.putExtra("weather",weather1);
            startActivity(intent);
        }
        else {
            Toast.makeText(Weather_Activity.this,"查询的地点不存在",Toast.LENGTH_SHORT).show();
            Log.d("dht","没有找到");
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather2);
        initView();
        weather = getIntent().getParcelableExtra("weather");
        Log.d("dht","----收到天气----"+weather.toString());
        String we = weather.getWeather();

        switch (we) {
            case "暴雪":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                break;
            case "暴雨":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                break;
            case "大暴雨":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                break;
            case "大雪":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_daxue);
                break;
            case "大雨":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_dayu);
                break;
            case "多云":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                break;
            case "雷阵雨":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                break;
            case "雷阵雨并伴有冰雹":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                break;
            case "晴":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_qing);
                break;
            case "沙尘暴":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                break;
            case "雾":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_wu);
                break;
            case "小雪":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                break;
            case "小雨":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                break;
            case "阴":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_yin);
                break;
            case "雨夹雪":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                break;
            case "阵雪":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                break;
            case "阵雨":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                break;
            case "中雪":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                break;
            case "中雨":
                iv_weather.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                break;
        }

        sp_city.setText(weather.getProvince()+" "+weather.getCity());
        tv_tem.setText(weather.getTemperature());
        tv_weather.setText(weather.getWeather());
        tv_tem_low_high.setText(weather.getHumidity());
        tv_win.setText("风向:"+weather.getWinddirection()+"风力:"+weather.getWindpower());
        tv_air.setText(weather.getReporttime());
        likeShared = getSharedPreferences("like",MODE_PRIVATE);

        if (!likeShared.getString(weather.getCity(),"").equals("")){
            like.setText("取消关注");
        }

        sp_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = weather.getCity();
                getWeatherOfCity(city);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = like.getText().toString();
                SharedPreferences.Editor editor = likeShared.edit();
                if (str.equals("取消关注")){
                    editor.remove(weather.getCity());
                    Toast.makeText(Weather_Activity.this, "取消关注" + weather.getCity(), Toast.LENGTH_SHORT).show();
                }else {
                    editor.putString(weather.getCity(), "1");
                    Toast.makeText(Weather_Activity.this, "添加关注" + weather.getCity(), Toast.LENGTH_SHORT).show();
                }
                editor.apply();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Weather_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void initView(){

        sp_city = (TextView) findViewById(R.id.sp_city);
        tv_tem = (TextView) findViewById(R.id.tv_tem);
        tv_weather = (TextView) findViewById(R.id.tv_weather);
        tv_tem_low_high = (TextView) findViewById(R.id.tv_tem_low_high);
        tv_win = (TextView) findViewById(R.id.tv_win);
        tv_air = (TextView) findViewById(R.id.tv_air);
        iv_weather = (ImageView) findViewById(R.id.iv_weather);
        like = (TextView) findViewById(R.id.like);
        back = (TextView) findViewById(R.id.back);

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