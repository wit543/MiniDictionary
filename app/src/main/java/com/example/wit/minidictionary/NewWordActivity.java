package com.example.wit.minidictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
    private ListView translationsListView;
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
        translationsListView = (ListView)findViewById(R.id.transaltions_list_view);


    }

    private void saveWord(String word,String pronunciation,ArrayList<Definition> translations){
        Word newWord = new Word(word);
        Storage.getInstance().addWord(newWord);
    }
}