package com.example.wit.minidictionary.models;

import com.example.wit.minidictionary.word.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIT on 02-Mar-16.
 */
public class Storage {
    private List<Word> words;
    private static Storage storage;
    private Storage(){
        words = new ArrayList<Word>();
    }
    public static Storage getInstance(){
        if(storage==null)
            storage = new Storage();
        return storage;
    }
    public void addWord(Word word){
        words.add(word);
    }
    public void deleteWord(Word word){
        words.remove(word);
    }
    public void deleteWord(String word){
        for(Word w:words)
            if(w.getWord().equals(word))
                words.remove(w);
    }
    public List<Word> loadWord(){
        return words;
    }
}
