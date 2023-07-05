package com.example.shiyan4;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


//这是一个Service服务类
public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    private String songName;
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder(this);
    }

    public class MusicBinder extends Binder {
        private MusicService musicService;
        public MusicBinder(){};
        public MusicBinder(MusicService musicService){
            this.musicService = musicService;
        }
        public void up(String songName){
            musicService.up(songName);
        }
        public void pausePlay(){
            mediaPlayer.pause();
        }
        public void continuePlay(){
            mediaPlayer.start();
        }
        public void seekTo(int progress){
            mediaPlayer.seekTo(progress);
        }
        public void newSong(String songName)  {
            mediaPlayer.reset();
            Log.d("dht","开始播放:"+songName);
            String musicPath = "/storage/emulated/0/Music/"+songName+".mp3";
            try {
                mediaPlayer.setDataSource(musicPath);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
            catch (Exception e){
                Log.d("dht","切换错误");
            }
        }
    }
    private void up(String songName){
        play(songName);
    }
    private void play(String songName){
        Log.d("dht","正在播放:"+songName);
        String musicPath = "/storage/emulated/0/Music/"+songName+".mp3";
        Log.d("dht",musicPath);
        try {
            mediaPlayer.setDataSource(musicPath);
            Log.d("dht","设置成功");
            mediaPlayer.prepare();
            Log.d("dht","准备完成");
            mediaPlayer.start();
            Log.d("dht","开始播放");
            addTimer();
            Log.d("dht","开始计时");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void addTimer(){
        if (timer == null){
            timer = new Timer();
            TimerTask task= new TimerTask(){
                public void run(){

                    if (mediaPlayer == null) return;
                    int duration=mediaPlayer.getDuration();//获取歌曲总时长
                    int currentPosition=mediaPlayer.getCurrentPosition();//获取播放进度
                    Message msg= MusicActivity.handler.obtainMessage();//创建消息对象
                    //将音乐的总时长和播放进度封装至bundle中
                    Bundle bundle=new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);
                    //再将bundle封装到msg消息对象中
                    msg.setData(bundle);
                    //最后将消息发送到主线程的消息队列
                    MusicActivity.handler.sendMessage(msg);
                }
            };
            //开始计时任务后的5毫秒，第一次执行task任务，以后每500毫秒（0.5s）执行一次
            timer.schedule(task,5,500);
        }
    }

}

