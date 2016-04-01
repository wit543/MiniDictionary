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
    public Word(String word , String pronunciation) {
        this.word = word;
        this.pronunciation = pronunciation;
        definitions = new ArrayList<Definition>();
    }

    public Word(String word , String pronunciation , List<Definition> definitions) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.definitions = definitions;
    }

    public void addDefinition(String partOfSpeech, String definition){
        definitions.add(new Definition(partOfSpeech,definition));
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public void setSynonyms(List<Word> synonyms) {
        this.synonyms = synonyms;
    }

    public void setAntonyms(List<Word> antonyms) {
        this.antonyms = antonyms;
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
