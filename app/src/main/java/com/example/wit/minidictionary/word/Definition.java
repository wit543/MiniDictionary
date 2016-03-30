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
    private List<Word> synonyms;

    public Definition(String partOfSpeech, String definition) {
        this.partOfSpeech = PartOfSpeech.valueOf(partOfSpeech.toUpperCase());
        this.definition = definition;
        synonyms = new ArrayList<Word>();
    }

    public Definition(String partOfSpeech, String definition, List<Word> synonyms) {
        this.partOfSpeech = PartOfSpeech.valueOf(partOfSpeech.toUpperCase());
        this.definition = definition;
        this.synonyms = synonyms;
    }

    public Definition(PartOfSpeech partOfSpeech, String definition) {
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
    }

    public void addSynonym(Word word){
        synonyms.add(word);
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getDefinition() {
        return definition;
    }
}
