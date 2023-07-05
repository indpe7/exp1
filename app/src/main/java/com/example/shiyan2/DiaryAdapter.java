package com.example.shiyan2;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DiaryAdapter extends ArrayAdapter<Diary> {
    private int resource;
    private Context mContext;
    public DiaryAdapter(Context context, int textViewResourceId, List<Diary> objects){
        super(context,textViewResourceId,objects);
        this.resource = textViewResourceId;
        this.mContext = context;
    }
    public View getView(int position,View convertView,ViewGroup parent){
       Diary diary = getItem(position);
       View view = LayoutInflater.from(getContext()).inflate(resource,null);
       TextView textView = (TextView) view.findViewById(R.id.textView);
       TextView textView1 = (TextView) view.findViewById(R.id.TimeView);
       String str="作品标题:"+diary.getTitle();
       String str1="上一次更改的时间:"+diary.getCreateTime();
       textView.setText(str);
       textView1.setText(str1);
       return view;
    }
}
