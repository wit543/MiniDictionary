package com.example.wit.minidictionary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.models.Storage;
import com.example.wit.minidictionary.views.TranslationAdapter;
import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.word.Word;

import java.util.ArrayList;
import java.util.List;

public class WordActivity extends AppCompatActivity {

    private Word word;

    private ListView wordListView;
    private List<Definition> definitions;
    private TranslationAdapter translationAdapter;

    private TextView subject;
    private TextView translation;
    private TextView partOfSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = (Word)getIntent().getSerializableExtra("word");
        setContentView(R.layout.activity_word_info);
        initComponents();
    }

    private void initComponents(){
        definitions = new ArrayList<Definition>();
        translationAdapter = new TranslationAdapter(this,R.layout.translation_cell,definitions);
        wordListView = (ListView)findViewById(R.id.definition_list_view);
        wordListView.setAdapter(translationAdapter);

        subject = (TextView)findViewById(R.id.subject);

    }

    protected void onStart(){
        super.onStart();
        subject.setText(word.getWord());
        //definitions.clear();
        for(Definition definition: word.getDefinition()){
            definitions.add(definition);
        }
        translationAdapter.notifyDataSetChanged();
    }
}
