package com.example.wit.minidictionary.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.views.DeletableWordAdapter;
import com.example.wit.minidictionary.views.TranslationAdapter;
import com.example.wit.minidictionary.views.WordAdapter;
import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.word.Word;

import java.util.ArrayList;
import java.util.List;

public class NewDefinitionActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView meaningField;
    private GridView synonymGridView;
    private Button addDefinitionButton;
    private Button addSynonymButton,cancelButton;

    private List<Word> synonyms;
    private DeletableWordAdapter wordAdapter;


    private static final int REQUEST_ADD_NEW_SYNONYM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_definition);
        this.initComponent();

    }

    private void initComponent(){

        synonyms = new ArrayList<Word>();

        spinner = (Spinner)findViewById(R.id.new_word_part_of_speech_spinner);
        meaningField = (TextView)findViewById(R.id.meaningField);
        synonymGridView = (GridView)findViewById(R.id.synonum_grid_view);
        addSynonymButton = (Button)findViewById(R.id.addSynonymButton);
        addDefinitionButton = (Button)findViewById(R.id.addDefinitionButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);

        wordAdapter = new DeletableWordAdapter(this,R.layout.word_cell , synonyms);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.type_of_part_of_speech,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        synonymGridView.setAdapter(wordAdapter);

        addSynonymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewDefinitionActivity.this, SearchWordActivity.class);
                startActivityForResult(intent, REQUEST_ADD_NEW_SYNONYM);
            }
        });

        addDefinitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = spinner.getSelectedItem().toString();
                String meaning = "" + meaningField.getText();
                Definition definition = new Definition(pos, meaning , synonyms);
                Intent intent = new Intent();
                intent.putExtra("definition", definition);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_NEW_SYNONYM&& resultCode == Activity.RESULT_OK) {
            Word word = (Word)data.getSerializableExtra("word");
            if(word!=null){
                synonyms.add(word);
                wordAdapter.notifyDataSetChanged();
            }
        }
    }
}
