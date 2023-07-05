package com.example.shiyan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AuthorActivity extends AppCompatActivity {
    EditText editText,editText1;
    String authorD;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        sharedPreferences = getSharedPreferences("author", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        authorD = getIntent().getStringExtra("auth");
        editText = (EditText) findViewById(R.id.authorInformation);
        editText1 = (EditText) findViewById(R.id.authorName);
        Object value = sharedPreferences.getString(authorD,"");
        editText1.setText(authorD);
        editText.setText(value.toString());
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.author_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.authorBack) {
            Intent intent = new Intent(AuthorActivity.this, ThirdActivity.class);
            startActivity(intent);
            return true;
        }
        else if (itemId == R.id.authorSave){
            String key = editText1.getText().toString();
            if (key.isEmpty()){
                Toast.makeText(AuthorActivity.this,"作者不能为空",Toast.LENGTH_SHORT).show();
                return false;
            }
            String value = editText.getText().toString();
            editor.putString(key, value);
            editor.apply();
            Intent intent = new Intent(AuthorActivity.this,ThirdActivity.class);
            startActivity(intent);
        }
        else if(itemId == R.id.authorDelete){
            String key = editText1.getText().toString();
            editor.remove(key);
            editor.apply();
            Intent intent = new Intent(AuthorActivity.this,ThirdActivity.class);
            startActivity(intent);
        }
        else if (itemId == R.id.authorSwitch){
            String key = editText1.getText().toString();
            Intent intent = new Intent(AuthorActivity.this,FirstActivity.class);
            intent.putExtra("a",key);
            startActivity(intent);
        }
        return false;
    }
}