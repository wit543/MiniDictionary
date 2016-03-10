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
public class TranslationAdapter extends ArrayAdapter<Definition> {

    private List<Definition> objects;

    public TranslationAdapter(Context context, int resource, List<Definition> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.translation_cell, null);
        }

        TextView posView = (TextView) v.findViewById(R.id.pos);
        TextView meaningView = (TextView) v.findViewById(R.id.meaning);
        Button deleteButton = (Button) v.findViewById(R.id.deleteTranslation);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objects.remove(position);
                notifyDataSetChanged();
            }
        });

        Definition definition = getItem(position);
        posView.setText(definition.getPartOfSpeech().toString());
        meaningView.setText(definition.getDefinition());

        return v;
    }

}
