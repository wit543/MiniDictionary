package com.example.wit.minidictionary.word;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIT on 01-Mar-16.
 */
public class Word implements Serializable{
    private String word;
    private String pronunciation;
    private List<Definition> definitions;
    private List<Word> synonyms;
    private List<Word> antonyms;
    public Word(String word) {
        this.word = word;
        definitions = new ArrayList<Definition>();
    }

    public void addDefinition(String partOfSpeech, String definition){
        definitions.add(new Definition(partOfSpeech,definition));
    }

    public void addDefinition(Definition definition){
        definitions.add(definition);
    }

    public String getWord() {
        return word;
    }

    public List<Definition> getDefinition() {
        return definitions;
    }

}
