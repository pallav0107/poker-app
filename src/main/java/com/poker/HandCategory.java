package com.poker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HandCategory {
    HIGH_CARD(1, "HIGH_CARD"),
    ONE_PAIR(2, "ONE_PAIR"),
    TWO_PAIRS(3, "TWO_PAIRS"),
    THREE_OF_A_KIND(4, "THREE_OF_A_KIND"),
    STRAIGHT(5, "STRAIGHT"),
    FLUSH(6, "FLUSH"),
    FULL_HOUSE(7, "FULL_HOUSE"),
    FOUR_OF_A_KIND(8, "FOUR_OF_A_KIND"),
    STRAIGHT_FLUSH(9, "STRAIGHT_FLUSH"),
    ROYAL_FLUSH(10, "ROYAL_FLUSH");

    private final int value;
    private final String desc;
}
