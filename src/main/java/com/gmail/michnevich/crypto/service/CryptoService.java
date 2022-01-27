package com.gmail.michnevich.crypto.service;

import com.gmail.michnevich.crypto.model.Currency;
import com.gmail.michnevich.crypto.model.Notification;
import com.gmail.michnevich.crypto.model.Tickers;
import com.gmail.michnevich.crypto.repository.CurrencyRepository;
import com.gmail.michnevich.crypto.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class CryptoService {

    private static final String COIN_LORE_URL = "https://api.coinlore.net/api/tickers/";

    private final CurrencyRepository currencyRepository;
    private final NotificationRepository notificationRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public CryptoService(CurrencyRepository currencyRepository, NotificationRepository notificationRepository) {
        this.currencyRepository = currencyRepository;
        this.notificationRepository = notificationRepository;
    }

    @PostConstruct
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void updateAndNotify() {
        ResponseEntity<Tickers> response = restTemplate.getForEntity(COIN_LORE_URL, Tickers.class);
        currencyRepository.saveAll(response.getBody().getData());

        for (Notification notification : notificationRepository.findByIsDoneFalse()) {
            double fixedPrice = notification.getFixedPrice();
            double currentPrice = findBySymbol(notification.getSymbol()).getPrice();
            double difference = Math.abs((fixedPrice - currentPrice) / fixedPrice) * 100;
            if (difference >= 1) {
                log.warn("User {}, symbol {}, price changed on {} percents",
                        notification.getUsername(), notification.getSymbol(), difference);
                notification.setDone(true);
                notificationRepository.save(notification);
            }
        }
    }

    @Transactional
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Transactional
    public Currency findBySymbol(String symbol) {
        return currencyRepository.findBySymbol(symbol.toUpperCase());
    }

    @Transactional
    public void notify(String username, String symbol) {
        notificationRepository.save(new Notification(username, symbol, findBySymbol(symbol).getPrice()));
    }


}
