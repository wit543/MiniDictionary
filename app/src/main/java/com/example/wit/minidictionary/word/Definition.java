package com.example.wit.minidictionary.word;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIT on 01-Mar-16.
 */
public class Definition implements Serializable {
    //private PartOfSpeech partOfSpeech;
    private PartOfSpeech partOfSpeech;
    private String definition;
    private List<Word> symWords;

    public Definition(String partOfSpeech, String definition) {
        this.partOfSpeech = PartOfSpeech.valueOf(partOfSpeech.toUpperCase());
        this.definition = definition;
        symWords = new ArrayList<>();
        //initSymList();
    }


    public Definition(String partOfSpeech, String definition , List<Word> synonyms) {
        this.partOfSpeech = PartOfSpeech.valueOf(partOfSpeech.toUpperCase());
        this.definition = definition;
        symWords = synonyms;
        //initSymList();
    }

    public Definition(PartOfSpeech partOfSpeech, String definition) {
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
        symWords = new ArrayList<>();
        //initSymList();
    }

    //    public void initSymList(){
//        symWords.add(new Word("aaa"));
//        symWords.add(new Word("bbb"));
//
//    }
    public void addSymnonym(Word word){
        symWords.add(word);
    }
    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getDefinition() {
        return definition;
    }

    public List<Word> getSymWords(){
        return symWords;
    }
}
