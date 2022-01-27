package com.gmail.michnevich.crypto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String symbol;
    private double fixedPrice;
    private boolean isDone;

    public Notification(String username, String symbol, double fixedPrice) {
        this.username = username;
        this.symbol = symbol;
        this.fixedPrice = fixedPrice;
    }
}
