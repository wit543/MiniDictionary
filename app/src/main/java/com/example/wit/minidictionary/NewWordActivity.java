package com.example.wit.minidictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.models.Storage;
import com.example.wit.minidictionary.word.Word;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewWordActivity extends AppCompatActivity {

    private Button addWordButton;
    private TextView wordField;
    private TextView pronunciationField;

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

        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWord(""+wordField.getText() ,""+pronunciationField.getText() , null);
                finish();
            }
        });
        Spinner spinner = (Spinner)findViewById(R.id.new_word_part_of_speech_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.type_of_part_of_speech,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    private void saveWord(String word,String pronunciation,ArrayList<Definition> translations){
        Word newWord = new Word(word);
        Storage.getInstance().addWord(newWord);
    }
}