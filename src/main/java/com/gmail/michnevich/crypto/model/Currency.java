package com.gmail.michnevich.crypto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Currency {

    @Id
    private Long id;
    @Column(unique = true)
    private String symbol;
    @JsonProperty("price_usd")
    private double price;

    public Currency(Long id, String symbol, double price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
    }
}
