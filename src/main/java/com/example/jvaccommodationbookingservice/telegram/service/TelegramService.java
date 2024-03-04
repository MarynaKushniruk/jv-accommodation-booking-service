package com.example.jvaccommodationbookingservice.telegram.service;

import com.example.jvaccommodationbookingservice.telegram.model.BotChat;

import java.util.List;

public interface TelegramService {
    void saveChatId(Long chatId);

    List<BotChat> findAll();
}
