package com.poker;

import lombok.Getter;

@Getter
public class Card implements Comparable<Card> {
    private final int value;
    private final char suit;

    public Card(String string) {
        char val = string.charAt(0);
        switch (val) {
            case 'T':
                this.value = 10;
                break;
            case 'J':
                this.value = 11;
                break;
            case 'Q':
                this.value = 12;
                break;
            case 'K':
                this.value = 13;
                break;
            case 'A':
                this.value = 14;
                break;
            default:
                this.value = Integer.parseInt(String.valueOf(val));
                break;
        }
        this.suit = string.charAt(1);
    }

    @Override
    public int compareTo(Card card) {
        int compareValue = card.getValue();
        return this.value - compareValue;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value) + this.suit;
    }
}
