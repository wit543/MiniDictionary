package com.example.wit.minidictionary.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.word.Word;

import java.util.List;

/**
 * Created by WIT on 01-Mar-16.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    public WordAdapter(Context context, int resource, List<Word> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null) {
            LayoutInflater vl = LayoutInflater.from(getContext());
            v = vl.inflate(R.layout.word_cell, null);
        }
        TextView wordView = (TextView) v.findViewById(R.id.word);
        Word word = getItem(position);
        wordView.setText(word.getWord());


        return v;
    }
}
