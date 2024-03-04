package com.example.jvaccommodationbookingservice.telegram.repository;

import com.example.jvaccommodationbookingservice.telegram.model.BotChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelegramRepository extends JpaRepository<BotChat, Long> {
    Optional<BotChat> findByChatId(Long chatId);
}
