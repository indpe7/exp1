package com.example.shiyan2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AuthorAdapter extends ArrayAdapter<Author> {
    private int resource;
    public AuthorAdapter(Context context, int textViewResourceId, List<Author> objects){
        super(context,textViewResourceId,objects);
        this.resource = textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Author str = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,null);
        TextView textView1 = (TextView) view.findViewById(R.id.de);

        TextView textView = (TextView) view.findViewById(R.id.authorView);
        textView.setText(str.toString());
        if (str.isNow()){
            textView1.setText("now");
        }
        return view;
    }
}
