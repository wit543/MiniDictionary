package com.example.wit.minidictionary.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.word.Word;

import java.util.List;

/**
 * Created by UMAMI on 3/7/2016.
 */
public class DeletableWordAdapter extends ArrayAdapter<Word> {

    private List<Word> objects;

    public DeletableWordAdapter(Context context, int resource, List<Word> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.deletable_word_cell, null);
        }

        TextView wordView = (TextView) v.findViewById(R.id.word);
        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objects.remove(position);
                notifyDataSetChanged();
            }
        });

        Word word = getItem(position);
        wordView.setText(word.getWord());

        return v;
    }

}
