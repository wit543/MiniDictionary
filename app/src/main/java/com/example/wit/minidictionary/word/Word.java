package com.example.wit.minidictionary.word;

import java.util.ArrayList;

/**
 * Created by WIT on 01-Mar-16.
 */
public class Word {
    private String word;
    private ArrayList<Definition> definitions;

    public Word(String word) {
        this.word = word;
        definitions = new ArrayList<Definition>();
    }

    public void addDefinition(String partOfSpeech, String definition){
        definitions.add(new Definition(PartOfSpeech.valueOf(partOfSpeech),definition));
    }
    public String getWord() {
        return word;
    }

    public ArrayList<Definition> getDefinition() {
        return definitions;
    }
}
