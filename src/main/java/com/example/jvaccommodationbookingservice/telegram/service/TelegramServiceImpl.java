package com.example.jvaccommodationbookingservice.telegram.service;


import com.example.jvaccommodationbookingservice.telegram.model.BotChat;
import com.example.jvaccommodationbookingservice.telegram.repository.TelegramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
    private final TelegramRepository telegramRepository;

    @Override
    public void saveChatId(Long chatId) {
        if (telegramRepository.findByChatId(chatId).isEmpty()) {
            BotChat botChat = new BotChat();
            botChat.setChatId(chatId);
            telegramRepository.save(botChat);
        }
    }

    @Override
    public List<BotChat> findAll() {
        return telegramRepository.findAll();
    }
}
