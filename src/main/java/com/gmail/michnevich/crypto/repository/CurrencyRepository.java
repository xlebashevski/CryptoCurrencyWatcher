package com.gmail.michnevich.crypto.repository;

import com.gmail.michnevich.crypto.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findBySymbol(String symbol);

}
