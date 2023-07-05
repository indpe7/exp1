package com.example.shiyan4;



import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MusicActivity extends AppCompatActivity {

    String songName;
    MusicService.MusicBinder musicBinder;
    Button pause,start,preSong,nextSong,back;
    static SeekBar seekBar;
    static TextView now,total;
    private boolean isUnbind =false;
    TextView name;
    SharedPreferences songs;
    List<String> bo;
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //服务建立,传递信息
            Log.d("dht","服务建立,传递信息");
            musicBinder = (MusicService.MusicBinder) iBinder;
            musicBinder.up(songName);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //获得传入数据
        songName = getIntent().getStringExtra("songName");

        Log.d("dht","收到歌曲"+songName);
        //初始化
        init();

        //显示播放名称
        name.setText("正在播放:" + songName);
        //获取播放列表
        getBo();
        //开始播放
        startMusicService();

        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentIndex = bo.indexOf(songName);
                int nextIndex = (currentIndex + 1) % bo.size(); // 使用取模运算来处理最后一个元素的情况
                String nextElement = bo.get(nextIndex);
                Log.d("dht","下一首:"+nextElement);
                musicBinder.newSong(nextElement);
                songName = nextElement;
                name.setText("正在播放:" + songName);
            }
        });
        preSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentIndex = bo.indexOf(songName);
                int previousIndex = (currentIndex - 1 + bo.size()) % bo.size(); // 使用取模运算来处理第一个元素的情况
                String previousElement = bo.get(previousIndex);
                Log.d("dht","上一首:"+previousElement);
                musicBinder.newSong(previousElement);
                songName = previousElement;
                name.setText("正在播放:" + songName);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicBinder.continuePlay();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicBinder.pausePlay();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stop = new Intent(MusicActivity.this,MusicService.class);
                stopService(stop);
                finish();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == seekBar.getMax()) {
                    Log.d("dht",songName+"播放完毕");
                    int currentIndex = bo.indexOf(songName);
                    int nextIndex = (currentIndex + 1) % bo.size(); // 使用取模运算来处理最后一个元素的情况
                    String nextElement = bo.get(nextIndex);
                    Log.d("dht","下一首:"+nextElement);
                    musicBinder.newSong(nextElement);
                    songName = nextElement;
                    name.setText("正在播放:" + songName);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                musicBinder.seekTo(progress);
            }
        });

    }
    public void getBo(){
        Map<String,?> map = songs.getAll();
        for (Map.Entry<String,?> map1:map.entrySet()){
            String key = map1.getKey();
            String str = songs.getString(key,"");
            String[] name = str.split(" ");
            if (FileChecker(name[0])){
                bo.add(name[0]);
            }
        }
        Log.d("dht","播放列表"+bo.toString());
    }
    public boolean FileChecker(String songName) {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File tempFile = new File(storageDir, songName + ".mp3");
        return tempFile.exists();
    }

    public void init(){
        bo = new ArrayList<>();
        songs = getSharedPreferences("songs",MODE_PRIVATE);
        pause = (Button) findViewById(R.id.pauseButton);
        start = (Button) findViewById(R.id.playButton);
        preSong = (Button) findViewById(R.id.preSong);
        nextSong = (Button) findViewById(R.id.nextSong);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        now = (TextView) findViewById(R.id.now);
        back = (Button) findViewById(R.id.back);
        total = (TextView) findViewById(R.id.total);
        name = (TextView) findViewById(R.id.songName);
    }
    private void startMusicService(){
        //bind方式启动
        Intent intent = new Intent(this,MusicService.class);

        bindService(intent,conn,BIND_AUTO_CREATE);
    }
    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler(){
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            int duration = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");
            seekBar.setMax(duration);
            seekBar.setProgress(currentPosition);
            int minute = duration/1000/60;
            int second = duration/1000%60;
            String strMinute = null;
            String strSecond = null;
            if (minute<10){
                strMinute = "0"+minute;
            }else{
                strMinute = minute+"";
            }
            if (second<10){
                strSecond = "0"+minute;
            }else{
                strSecond = minute+"";
            }
            total.setText(strMinute+":"+strSecond);
            minute=currentPosition/1000/60;
            second=currentPosition/1000%60;
            if(minute<10){
                strMinute="0"+minute;
            }else{
                strMinute=minute+" ";
            }
            if (second<10){
                strSecond="0"+second;
            }else{
                strSecond=second+" ";
            }
            now.setText(strMinute+":"+strSecond);
        }
    };
    private void unbind(boolean isUnbind){
        //如果解绑了
        if(!isUnbind){
            musicBinder.pausePlay();//音乐暂停播放
            unbindService(conn);//解绑服务
        }
    }
}

