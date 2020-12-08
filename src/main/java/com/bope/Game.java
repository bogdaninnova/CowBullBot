package com.bope;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String word;
    private String lang;

    public Game(String word) {
        setWord(word);
        setLang(CowBullBot.defaultLang);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCowsAndBulls(String wordCheck) {
        if (word.length() != wordCheck.length()) {
            String letters = " букв!";
            if (word.length() < 5)
                letters = " буквы!";
            return "Неправильное количество букв! Я загадал слово на " + word.length() + letters;
        }
        int bulls = 0;
        int cows = 0;


        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == wordCheck.charAt(i)) {
                bulls++;
            } else {
                list1.add(String.valueOf(word.charAt(i)));
                list2.add(String.valueOf(wordCheck.charAt(i)));
            }
        }

        for (String ch : list1) {
            if (list2.remove(ch))
                cows++;
        }

        StringBuilder result = new StringBuilder(wordCheck).append(":");

        if (bulls != 0) {
            if (bulls == 1)
                result.append(" ").append(bulls).append(" бык");
            else
                result.append(" ").append(bulls).append(" быка");
        }
        if (cows != 0) {
            if (cows == 1)
                result.append(" ").append(cows).append(" корова");
            else
                result.append(" ").append(cows).append(" коровы");
        }
        if (cows+bulls == 0)
            result.append(" ничего");

        return result.toString();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
