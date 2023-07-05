package com.example.shiyan3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LIstActivity extends AppCompatActivity {
    SharedPreferences cities;
    ListView province;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        province = (ListView) findViewById(R.id.province);
        cities = getSharedPreferences("city",MODE_PRIVATE);
        List<String> citys = new ArrayList<>();
        Map<String, ?> allEntries = cities.getAll();
        for (Map.Entry<String,?> entry : allEntries.entrySet()){
            String key =entry.getKey();
            citys.add(key);
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,citys);
        province.setAdapter(adapter);
        province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String p = citys.get(i);
                Intent intent = new Intent(LIstActivity.this,CityActivity.class);
                intent.putExtra("province",p);
                startActivity(intent);
            }
        });
    }
}