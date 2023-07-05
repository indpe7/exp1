package com.example.shiyan4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView musicList;
    SharedPreferences songs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        IntentFilter intentFilter = new IntentFilter("com.example.musicplayer.DOWNLOAD_COMPLETE");
        BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String song_name = intent.getStringExtra("song_name");
                Log.d("dht","收到广播:"+song_name+"下载完毕");
                Intent intent1 = new Intent(MainActivity.this,MusicActivity.class);
                intent1.putExtra("songName",song_name);
                startActivity(intent1);
            }
        };
        registerReceiver(downloadCompleteReceiver, intentFilter);
//        Intent intent = new Intent(MainActivity.this,MusicActivity.class);
//        startActivity(intent);
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String stS = songs.getString(String.valueOf(i+1),"");
                String[] str = stS.split(" ");
                String name = str[0];
                String url = str[1];

                if (!FileChecker(name)) {
                    Log.d("dht","文件不存在,开始下载");
                    Intent intent = new Intent(MainActivity.this, DownloadService.class);
                    intent.putExtra("url", url);
                    intent.putExtra("name", name);
                    startService(intent);
                }
                else{
                    Log.d("dht","文件存在,开始播放");
                    Intent intent = new Intent(MainActivity.this,MusicActivity.class);
                    intent.putExtra("songName",name);
                    startActivity(intent);
                }
            }
        });


    }
    public boolean FileChecker(String songName) {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File tempFile = new File(storageDir, songName + ".mp3");
            return tempFile.exists();
    }
    public void initView(){
        //获取列表
        musicList = (ListView) findViewById(R.id.musicList);
        songs = getSharedPreferences("songs",MODE_PRIVATE);
        //initSharedPreferences();
        List<String> songNames = new ArrayList<>();
        Map<String,?> map = songs.getAll();
        for (Map.Entry<String,?> map1:map.entrySet()){
            String name = map1.getKey();
            String str = songs.getString(name,"");
            String[] str1 = str.split(" ");
            songNames.add(str1[0]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,songNames);
        musicList.setAdapter(adapter);
    }
    public void initSharedPreferences(){
        SharedPreferences.Editor editor= songs.edit();
        editor.clear();
        editor.apply();

        String songName1 = "HELL";
        String songUrl1 = "https://freetyst.nf.migu.cn/public/product9th/product46/2022/08/1209/2022%E5%B9%B408%E6%9C%8811%E6%97%A522%E7%82%B940%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E9%82%93%E7%B4%AB%E6%A3%8B%E5%B7%A5%E4%BD%9C%E5%AE%A4421%E9%A6%96015202/%E6%A0%87%E6%B8%85%E9%AB%98%E6%B8%85/MP3_128_16_Stero/69031400001092840.mp3";
        String song1 = songName1+" "+songUrl1;
        editor.putString("1",song1);

        String songName2 = "光年之外";
        String songUrl2 = "https://freetyst.nf.migu.cn/public/product9th/product45/2022/04/2015/2017%E5%B9%B411%E6%9C%8808%E6%97%A517%E7%82%B914%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E7%88%B1%E7%A8%BB%E8%8D%891%E9%A6%96/%E6%A0%87%E6%B8%85%E9%AB%98%E6%B8%85/MP3_128_16_Stero/63273401958151038.mp3";
        String song2 = songName2+" "+songUrl2;
        editor.putString("2",song2);

        String songName3 = "千里之外";
        String songUrl3 = "https://freetyst.nf.migu.cn/public/product9th/product42/2021/02/0413/2018%E5%B9%B411%E6%9C%8809%E6%97%A519%E7%82%B905%E5%88%86%E6%89%B9%E9%87%8F%E9%A1%B9%E7%9B%AE%E5%8D%8E%E7%BA%B377%E9%A6%96-13/%E6%A0%87%E6%B8%85%E9%AB%98%E6%B8%85/MP3_128_16_Stero/6005751LD55130225.mp3";
        String song3 = songName3+" "+songUrl3;
        editor.putString("3",song3);

        String songName4 = "one_last_kiss";
        String songUrl4 = "https://ws.stream.qqmusic.qq.com/C400004TOhKh46sr9W.m4a?guid=4220211900&vkey=39A69E8C673C8BA9B29963E24329FD445931FFDBE671D4CEABD1DE6205C5D9E4BF71C9D7C95D70AB461D539DC4B3BD5F87405407B8B852EF&uin=&fromtag=123032";
        String song4 = songName4+" "+songUrl4;
        editor.putString("4",song4);

        String songName5 = "来自天堂的魔鬼";
        String songUrl5 = "https://ws.stream.qqmusic.qq.com/C400001DI2Jj3Jqve9.m4a?guid=4220211900&vkey=39EC0B9831AF139B11C882FACC992E6736F648B0702B88B9D83B556EB7461B990657E9DBCA11031C63119706B63A2D58731A8C9EBAC56B1C&uin=&fromtag=123032";
        String song5 = songName5+" "+songUrl5;
        editor.putString("5",song5);

        String songName6 = "再见";
        String songUrl6 = "https://freetyst.nf.migu.cn/public/product9th/product45/2021/12/1310/2021%E5%B9%B411%E6%9C%8826%E6%97%A515%E7%82%B901%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E5%98%89%E7%BE%8E%E4%B9%B0%E6%96%AD4236%E9%A6%96190374/%E6%A0%87%E6%B8%85%E9%AB%98%E6%B8%85/MP3_128_16_Stero/69002603070103247.mp3";
        String song6 = songName6+" "+songUrl6;
        editor.putString("6",song6);

        editor.apply();
    }


}