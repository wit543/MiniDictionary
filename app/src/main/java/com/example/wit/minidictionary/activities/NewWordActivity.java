package com.example.wit.minidictionary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.views.TranslationAdapter;
import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.models.Storage;
import com.example.wit.minidictionary.word.PartOfSpeech;
import com.example.wit.minidictionary.word.Word;

import java.util.ArrayList;
import java.util.List;

public class NewWordActivity extends AppCompatActivity {

    private Button addWordButton;
    private Button addTranslationButton;
    private TextView wordField;
    private TextView pronunciationField;
    private TextView translations_list_view;
    private Spinner spinner;
    private TextView meaningField, posSub , meaningSub;
    private ListView translationListView;

    private List<Definition> definitions;
    private TranslationAdapter translationAdapter;

    private int addTranslationState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        this.initComponent();
    }

    private void initComponent(){
        wordField = (TextView)findViewById(R.id.wordField);
        pronunciationField = (TextView)findViewById(R.id.pronunciationField);
        addWordButton = (Button)findViewById(R.id.addWordButton);
        meaningField = (TextView)findViewById(R.id.meaningField);
        posSub = (TextView)findViewById(R.id.partOfSpeechSubject);
        meaningSub = (TextView)findViewById(R.id.meaningSubjecr);
        spinner = (Spinner)findViewById(R.id.new_word_part_of_speech_spinner);
        addTranslationButton = (Button)findViewById(R.id.addTranslationButton);
        translationListView = (ListView)findViewById(R.id.transaltions_list_view);

        definitions = new ArrayList<Definition>();
        translationAdapter = new TranslationAdapter(this,R.layout.translation_cell,definitions);
        translationListView.setAdapter(translationAdapter);


        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWord("" + wordField.getText(), "" + pronunciationField.getText(), null);
                finish();
            }
        });


        addTranslationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addTranslationState == 0){
                    spinner.setVisibility(View.VISIBLE);
                    meaningField.setVisibility(View.VISIBLE);
                    posSub.setVisibility(View.VISIBLE);
                    meaningSub.setVisibility(View.VISIBLE);
                    //addTranslationButton.setVisibility(View.INVISIBLE);
                    addTranslationButton.setText("Add");
                    addTranslationState = 1;
                }
                else{
                    spinner.setVisibility(View.GONE);
                    meaningField.setVisibility(View.GONE);
                    posSub.setVisibility(View.GONE);
                    meaningSub.setVisibility(View.GONE);
                    addTranslationButton.setText("Add  Translation");
                    addTranslationState = 0;

                    addNewDefinition(spinner.getSelectedItem().toString(), ""+meaningField.getText());
                    }
            }
        });



        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.type_of_part_of_speech,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);



        spinner.setVisibility(View.GONE);
        meaningField.setVisibility(View.GONE);
        posSub.setVisibility(View.GONE);
        meaningSub.setVisibility(View.GONE);

      
    }

    private void addNewDefinition(String pos , String meaning){
        Definition newDefinition = new Definition( pos , meaning);
        definitions.add(newDefinition);
        translationAdapter.notifyDataSetChanged();
    }

    private void saveWord(String word,String pronunciation,ArrayList<Definition> translations){
        Word newWord = new Word(word);
        Storage.getInstance().addWord(newWord);
    }
}