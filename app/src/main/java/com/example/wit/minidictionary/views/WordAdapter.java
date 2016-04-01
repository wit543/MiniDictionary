package com.example.wit.minidictionary.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.word.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by WIT on 01-Mar-16.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    private HashMap<Integer,Boolean> selection= new HashMap<Integer,Boolean>();
    public WordAdapter(Context context, int resource, List<Word> objects) {
        super(context, resource, objects);
    }
    public void setNewSelection(int postion,boolean bool){
        selection.put(postion,bool);
        notifyDataSetChanged();
    }
    public boolean isPositionChecked(int position){
        Boolean result = selection.get(position);
        return result == null ? false:result;
    }
    public Set<Integer> getCurrentCheckedPosition(){
        return selection.keySet();
    }

    public void removeSelected(int position){
        selection.remove(position);
        notifyDataSetChanged();
    }
    public void clearSelection(){
        selection.clear();
        notifyDataSetChanged();
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null) {
            LayoutInflater vl = LayoutInflater.from(getContext());
            v = vl.inflate(R.layout.word_cell, null);
        }
        //v.setBackgroundColor(getContext().getColor(android.R.color.holo_blue_bright));
        v.setBackgroundColor(Color.BLUE);
        if(selection.get(position)!=null)
            v.setBackgroundColor(Color.CYAN);
            //v.setBackgroundColor(getContext().getColor(android.R.color.holo_blue_dark));

        TextView wordView = (TextView) v.findViewById(R.id.word);
        Word word = getItem(position);
        wordView.setText(""+word.getWord());

        return v;
    }

}
