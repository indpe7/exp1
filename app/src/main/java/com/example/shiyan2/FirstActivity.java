package com.example.shiyan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirstActivity extends AppCompatActivity {
    List<Diary> diaries;
    String Author="dht",authorD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        authorD = getIntent().getStringExtra("a");
        if (authorD!=null && !authorD.isEmpty()){
            Author = authorD;
        }


        DBHelper db = new DBHelper(this);

        //获取当前作者的作品
        String[] a = new String[1];
        a[0] = Author;
        diaries = db.getDiaries(a);

        //当前的作者的显示
        TextView nowAuthor = (TextView) findViewById(R.id.nowAuthor);
        nowAuthor.setText("当前作者为:"+Author);

        //日记列表的显示
        DiaryAdapter adapter = new DiaryAdapter(FirstActivity.this,R.layout.list_item,diaries);
        ListView listView = (ListView)findViewById(R.id.DiaryList);
        listView.setAdapter(adapter);

        //添加按钮的注册
        FloatingActionButton jia = findViewById(R.id.add);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Diary data = diaries.get(i);
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                intent.putExtra("data",data);
                intent.putExtra("author",Author);
                startActivity(intent);
            }
        });
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("author",Author);
                startActivity(intent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.menu_item2){
            Intent intent = new Intent(FirstActivity.this, ThirdActivity.class);
            intent.putExtra("now",Author);
            startActivity(intent);
            return true;
        }
        return false;
    }
}