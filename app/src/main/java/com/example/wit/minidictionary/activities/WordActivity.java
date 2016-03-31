package com.example.wit.minidictionary.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.models.Storage;
import com.example.wit.minidictionary.views.DefinitionSymnoAdapter;
import com.example.wit.minidictionary.views.TranslationAdapter;
import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.word.Word;

import java.util.ArrayList;
import java.util.List;

public class WordActivity extends AppCompatActivity {

    private Word word;

    private ListView definitionListView;
    private ListView symnoListView;
    private List<Definition> definitions;
    private DefinitionSymnoAdapter definitionSymnoAdapter;
    private List<Word> symnonym;

    private TextView subject;
    private TextView translation;
    private TextView partOfSpeech;
    private TextView pronunWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = (Word)getIntent().getSerializableExtra("word");
        setContentView(R.layout.activity_word_info);
        initComponents();
    }

    private void initComponents(){
        definitions = word.getDefinition();
        //symnonym = new ArrayList<Word>();
        definitionSymnoAdapter = new DefinitionSymnoAdapter(this,R.layout.def_sym_cell,definitions);
        definitionListView = (ListView)findViewById(R.id.definition_List_view);
        definitionListView.setAdapter(definitionSymnoAdapter);


        subject = (TextView)findViewById(R.id.subject);
        //symnoListView = (ListView)findViewById(R.id.symno_List_view);

    }

    protected void onStart(){
        super.onStart();
        subject.setText(word.getWord());
        //pronunWord.setText(word.getPronunciation());
        //definitions.clear();

        definitions = word.getDefinition();

        definitionSymnoAdapter.notifyDataSetChanged();
    }
}
