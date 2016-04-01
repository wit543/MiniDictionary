package com.example.wit.minidictionary.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.activities.WordActivity;
import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.word.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Momo on 29/3/2559.
 */
public class DefinitionSymnoAdapter extends ArrayAdapter<Definition>{

    private List<Definition> objects;
    private SymnoAdapter symnoAdapter;
    private List<Word> symnonyms;

    public DefinitionSymnoAdapter(Context context, int resource, List<Definition> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.def_sym_cell, null);
        }


        TextView pronunView = (TextView) v.findViewById(R.id.pronunciation);
        TextView meaningView = (TextView) v.findViewById(R.id.translation);
        ListView symWordView = (ListView) v.findViewById(R.id.symno_List_view);


        Definition definition = getItem(position);
        pronunView.setText(definition.getPartOfSpeech().toString());
        meaningView.setText(definition.getDefinition());

        symnonyms = definition.getSymWords();
        symnoAdapter = new SymnoAdapter(this.getContext(), R.layout.sym_cell, symnonyms );
        symWordView.setAdapter(symnoAdapter);
        symnoAdapter.notifyDataSetChanged();

        symWordView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppCompatActivity activity = (AppCompatActivity)getContext();
                Intent intent = new Intent(activity,WordActivity.class);
                intent.putExtra("word", symnonyms.get(position));
                activity.startActivity(intent);
                activity.finish();

            }
        });
        return v;
    }

}
