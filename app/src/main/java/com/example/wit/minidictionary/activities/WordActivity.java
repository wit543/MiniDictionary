package com.example.wit.minidictionary.activities;

import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.util.Locale;

public class WordActivity extends AppCompatActivity {

    private Word word;

    private ListView definitionListView;
    private ListView symnoListView;
    private List<Definition> definitions;
    private DefinitionSymnoAdapter definitionSymnoAdapter;
    private List<Word> symnonym;
    private TextToSpeech tts;
    private TextView subject;
    private TextView translation;
    private TextView partOfSpeech;
    private TextView pronunWord;

    private Button editButton,speakButton;
    private static final int REQUEST_EDIT_WORD = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = (Word)getIntent().getSerializableExtra("word");
        setContentView(R.layout.activity_word_info);
        initComponents();
    }

    private void initComponents(){

        tts = new TextToSpeech(WordActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
            }
        });

        definitions = word.getDefinition();
        definitionSymnoAdapter = new DefinitionSymnoAdapter(this,R.layout.def_sym_cell,definitions);
        definitionListView = (ListView)findViewById(R.id.definition_List_view);
        editButton = (Button)findViewById(R.id.editButton);
        definitionListView.setAdapter(definitionSymnoAdapter);
        speakButton = (Button)findViewById(R.id.speakButton);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordActivity.this, NewWordActivity.class);
                intent.putExtra("word", word);
                startActivity(intent);
                finish();
                //startActivityForResult(intent,REQUEST_EDIT_WORD);
            }
        });

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(word.getWord(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        subject = (TextView)findViewById(R.id.subject);
        //symnoListView = (ListView)findViewById(R.id.symno_List_view);

    }

    protected void onStart(){
        super.onStart();
        subject.setText(word.getWord());
        definitions = word.getDefinition();
        definitionSymnoAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT_WORD&& resultCode == Activity.RESULT_OK) {
            word = (Word)data.getSerializableExtra("word");
        }
    }
}
