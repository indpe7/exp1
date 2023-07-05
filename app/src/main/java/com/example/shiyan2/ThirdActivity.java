package com.example.shiyan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ThirdActivity extends AppCompatActivity {
    List<Author> authors;
    SharedPreferences sharedPreferences;
    String authorD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        authorD = getIntent().getStringExtra("now");
        //获取作者列表
        authors = new ArrayList<>();
        sharedPreferences = getSharedPreferences("author", MODE_PRIVATE);


        Map<String,?> allAuthor= sharedPreferences.getAll();

        for (Map.Entry<String,?> entry : allAuthor.entrySet()){
            String authorA = entry.getKey();
            boolean now = false;
            if (authorA.equals(authorD)) now = true;
            Author author = new Author(authorA,now);
            authors.add(author);
        }


        //显示作者列表
        AuthorAdapter adapter = new AuthorAdapter(ThirdActivity.this,R.layout.author_item,authors);
        ListView listView = (ListView) findViewById(R.id.AuthorList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String authorAU = authors.get(i).toString();
                Intent intent = new Intent(ThirdActivity.this,AuthorActivity.class);
                intent.putExtra("auth",authorAU);
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
        if (itemId == R.id.menu_item1) {
            Intent intent = new Intent(ThirdActivity.this, FirstActivity.class);
            intent.putExtra("a",authorD);
            startActivity(intent);
            return true;
        }
        return false;
    }
}