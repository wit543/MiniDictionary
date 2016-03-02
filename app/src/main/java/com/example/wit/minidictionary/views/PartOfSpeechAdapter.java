package com.example.wit.minidictionary.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.word.PartOfSpeech;

import java.util.List;

/**
 * Created by WIT on 02-Mar-16.
 */
public class PartOfSpeechAdapter extends ArrayAdapter<PartOfSpeech> {
    public PartOfSpeechAdapter(Context context, int resource, List<PartOfSpeech> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.)
        }
    }
}
