package com.bope;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cowbullvoc")
public class WordMongo {

    @Id
    private ObjectId id;
    private String lang;
    private String word;
    private int length;

    public WordMongo(String word, String lang) {
        setWord(word);
        setLang(lang);
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
