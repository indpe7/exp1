package com.example.shiyan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeleteActivity extends AppCompatActivity {
    String au;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Diary diary = getIntent().getParcelableExtra("di");
        au = diary.getAuthor();
        TextView textView = (TextView) findViewById(R.id.deleteView);
        String str = "是否要删除"+diary.getAuthor()+"的日记"+diary.getTitle();
        textView.setText(str);
        Button button = (Button) findViewById(R.id.sure);
        Button button1 = (Button) findViewById(R.id.notSure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(DeleteActivity.this);
                db.deleteDiary(diary.getId());
                db.close();
                Intent intent = new Intent(DeleteActivity.this, FirstActivity.class);
                intent.putExtra("a",au);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}