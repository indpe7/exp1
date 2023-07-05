package com.example.shiyan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    boolean edit = true;
    Diary diary;
    String authorD;
    EditText title,author,content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //获取传入的数据值
        diary = getIntent().getParcelableExtra("data");
        authorD = getIntent().getStringExtra("author");

        //对文本框进行注册
        title = (EditText) findViewById(R.id.title);
        author = (EditText) findViewById(R.id.author);
        content = (EditText) findViewById(R.id.content);

        if (diary!=null) {
            edit = true;
            title.setText(diary.getTitle());
            author.setText(diary.getAuthor());
            content.setText(diary.getContent());
        }
        else{
            edit = false;
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.diaryBack){
            Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
            intent.putExtra("a",authorD);
            startActivity(intent);
            return true;
        }
        else if (itemId==R.id.diarySave){

            DBHelper db = new DBHelper(SecondActivity.this);

            String titleValue = title.getText().toString();
            String authorValue = author.getText().toString();

            if (authorValue.isEmpty()){
                if (!authorD.isEmpty()){
                    authorValue = authorD;
                }else{
                    Toast.makeText(SecondActivity.this,"作者不能为空",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            SharedPreferences sharedPreferences = getSharedPreferences("author", MODE_PRIVATE);
            String v =sharedPreferences.getString(authorValue,"");
            if (v.equals("")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(authorValue, "12");
                editor.apply();
            }
            String contentValue = content.getText().toString();
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = sdf.format(currentTime);
            Diary diary1 = new Diary(0,titleValue,contentValue,currentDateTime,authorValue);
            if (edit){
                diary1.setId(diary.getId());
                db.updateDiary(diary1);
                Toast.makeText(SecondActivity.this,"修改",Toast.LENGTH_SHORT).show();
            }
            else{
                db.addDiary(diary1);
                Toast.makeText(SecondActivity.this,"添加",Toast.LENGTH_SHORT).show();
            }
            db.close();
            Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
            intent.putExtra("a",authorD);
            startActivity(intent);
        }
        else if (itemId==R.id.diaryDelete){
            Intent intent = new Intent(SecondActivity.this,DeleteActivity.class);
            intent.putExtra("di",diary);
            startActivity(intent);
        }
        return false;
    }
}