package com.example.wit.minidictionary.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.models.Storage;
import com.example.wit.minidictionary.views.WordAdapter;
import com.example.wit.minidictionary.word.Word;

import java.util.ArrayList;
import java.util.List;

public class SearchWordActivity extends AppCompatActivity {

    private List<Word> words;
    private List<Word> wordsFiltered;
    private String searchQuery;
    private GridView wordGridView;
    private EditText searchET;
    private WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);
        this.initComponent();
    }

    private void initComponent(){
        searchQuery = "";
        words = new ArrayList<Word>();
        wordsFiltered = words;
        wordGridView = (GridView) findViewById(R.id.words_grid_view);
        wordAdapter = new WordAdapter(this, R.layout.word_cell, words);
        wordGridView.setAdapter(wordAdapter);

        wordGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("word", words.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.search_word_bar);
        searchET = (EditText) actionBar.getCustomView()
                .findViewById(R.id.search_word);
        searchET.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = searchET.getText().toString();
                wordsFiltered = performSearch(words, searchQuery);
                loadSelectedWord();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //searchET.setText(queryText);
        searchET.requestFocus();
    }

    private void loadSelectedWord(){
        words.clear();
        for(Word w: wordsFiltered)
            words.add(w);
        wordAdapter.notifyDataSetChanged();
    }

    private List<Word> performSearch(List<Word> words, String text){
        String query = text.toLowerCase();
        // output list
        List<Word> wordsFiltered = new ArrayList<Word>();
        for(Word w :Storage.getInstance().loadWord()){
            if(w.getWord().contains(query)){
                wordsFiltered.add(w);
            }
        }
        return wordsFiltered;
    }

    public void onStart() {
        super.onStart();
        loadWords();

    }

    private void loadWords(){
        words.clear();
        for(Word w: Storage.getInstance().loadWord())
            words.add(w);
        wordAdapter.notifyDataSetChanged();
    }
}
