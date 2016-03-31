package com.example.wit.minidictionary.models;

import android.content.Context;
import android.content.ContextWrapper;

import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.word.Word;

import java.io.FileOutputStream;
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
        for(int i=0;i<100;i++){
            words.add(new Word(String.valueOf(i) , "FUCK"));
        }
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
            if(w.getWord().equals(word)) {
                words.remove(w);
                break;
            }
    }
    public void clearWord(){
        words.clear();
    }
    public List<Word> loadWord(){
        return words;
    }
    public void removeWord(Word word){
        words.remove(word);
    }

    public void editWord(Word original,String word,String pronunciation,List<Definition> definitions){
        for(Word w : words){
            if(w.getWord().equals(original.getWord())){
                w.setWord(word);
                w.setPronunciation(pronunciation);
                w.setDefinitions(definitions);
                break;
            }
        }
    }

}
