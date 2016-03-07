package com.example.wit.minidictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wit.minidictionary.views.TranslationAdapter;
import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.models.Storage;
import com.example.wit.minidictionary.word.Word;

import java.util.ArrayList;
import java.util.List;

public class NewWordActivity extends AppCompatActivity {

    private Button addWordButton , addTranslationButton;
    private TextView wordField;
    private TextView pronunciationField;
    private ListView translationsListView;
    private TranslationAdapter addTranslationAdapter;
    private List<Definition> translations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        this.initComponent();
    }

    private void initComponent(){

        translations = new ArrayList<Definition>();
        translations.add(new Definition("FUCk" , "FUCK"));

        wordField = (TextView)findViewById(R.id.wordField);
        pronunciationField = (TextView)findViewById(R.id.pronunciationField);
        addWordButton = (Button)findViewById(R.id.addWordButton);
        addTranslationButton = (Button)findViewById(R.id.addTranslationButton);

        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWord(""+wordField.getText() ,""+pronunciationField.getText() , null);
                finish();
            }
        });
        translationsListView = (ListView)findViewById(R.id.transaltions_list_view);
        addTranslationAdapter = new TranslationAdapter(this , R.layout.translation_input , translations);
        translationsListView.setAdapter(addTranslationAdapter);

        addTranslationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translations.add(new Definition("", ""));
                addTranslationAdapter.notifyDataSetChanged();
            }
        });



    }

    private void saveWord(String word,String pronunciation,ArrayList<Definition> translations){
        Word newWord = new Word(word);
        Storage.getInstance().addWord(newWord);
    }
}