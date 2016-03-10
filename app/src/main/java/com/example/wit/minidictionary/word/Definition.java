package com.example.wit.minidictionary.word;

import java.io.Serializable;

/**
 * Created by WIT on 01-Mar-16.
 */
public class Definition implements Serializable {
    //private PartOfSpeech partOfSpeech;
    private PartOfSpeech partOfSpeech;
    private String definition;

    public Definition(String partOfSpeech, String definition) {
        this.partOfSpeech = PartOfSpeech.valueOf(partOfSpeech.toUpperCase());
        this.definition = definition;
    }

    public Definition(PartOfSpeech partOfSpeech, String definition) {
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getDefinition() {
        return definition;
    }
}
