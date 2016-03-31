package com.example.wit.minidictionary.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.word.Word;

import java.util.List;

/**
 * Created by Momo on 31/3/2559.
 */
public class SymnoAdapter extends ArrayAdapter<Word>{

    public SymnoAdapter(Context context, int resource, List<Word> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.sym_cell , null);
        }
        return v;
    }

}
