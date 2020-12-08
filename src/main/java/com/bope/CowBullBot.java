package com.bope;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class CowBullBot extends TelegramLongPollingBot {

    private static final Random rand = new Random();
    private final Map<Long, Game> games = new HashMap<>();
    protected static final String defaultLang = "eng";

    public void onUpdateReceived(Update update) {

        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        int indexOf = text.indexOf('@');
        if (indexOf > 0)
           text = text.substring(0, text.indexOf('@'));
        User user = update.getMessage().getFrom();

        if (text.toLowerCase().startsWith("/start")) {
            int n = (text.length() > 7) ? getNumber(text) : 4;
            String lang = text.contains(" rus") ? "rus" : defaultLang;
            List<WordMongo> wordList = Main.ctx.getBean(WordsListMongo.class).findByLangAndLength(lang, n);
             if (wordList.size() != 0) {
                games.put(chatId, new Game(wordList.get(rand.nextInt(wordList.size()-1)).getWord()));
                sendSimpleMessage("Я загадал слово на " + n + " букв!", chatId);
            } else {
                sendSimpleMessage("Я не знаю слов с таким количеством букв :(", chatId);
            }
            return;
        }

        if (!games.containsKey(chatId)) {
            sendSimpleMessage("Игра ещё не началась. Для новой игры напиши /start", chatId);
            return;
        }

        if (text.equals("/giveup")) {
            sendSimpleMessage("Загаданное слово - " + games.get(chatId).getWord() + "! Для новой игры напиши /start", chatId);
            games.remove(chatId);
            return;
        }

        if (text.charAt(0) == '/')
            text = text.substring(1);

        if (games.get(chatId).getWord().equals(text.toLowerCase())) {
            sendSimpleMessage("Это правильный ответ! Для новой игры напиши /start", chatId);
            games.remove(chatId);
            return;
        }

        if (Main.ctx.getBean(WordsListMongo.class).existsByLangAndWord(games.get(chatId).getLang(), text))
            sendSimpleMessage(games.get(chatId).getCowsAndBulls(text), chatId);
        else
            sendSimpleMessage("Я не знаю такого слова :(", chatId);
    }


    private int getNumber(String text) {
        try {
            return Integer.parseInt(text.substring(text.lastIndexOf(' ') + 1));
        }
        catch (NumberFormatException e) {
            return 4;
        }
    }


    private void sendSimpleMessage(String text, long chatId) {
        SendMessage message = new SendMessage().setChatId(chatId).setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "cowbullgamebot";
    }

    public String getBotToken() {
        return "1423785004:AAGmOGWdtWbt-eRWQGJOcSvHHemoGpoUgtI";
    }
}
