package com.example.wit.minidictionary.word;

/**
 * Created by WIT on 01-Mar-16.
 */
public class Definition {
    private PartOfSpeech partOfSpeech;
    private String definition;

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
