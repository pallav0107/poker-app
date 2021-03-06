package com.poker;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hand {

    public Card[] cards;
    public HandCategory category;
    public Integer handValue;

    public Hand(Card[] cards) {
        this.cards = cards;
    }

    public Hand(String[] stringArray) {
        if (stringArray.length != 5) {
            log.error("Wrong hand format provided");
        } else {
            Card[] cardArray = new Card[5];
            for (int i = 0; i < 5; i++) {
                cardArray[i] = new Card(stringArray[i]);
            }
            this.cards = cardArray;
        }
    }

    public void sortCards() {
        Arrays.sort(this.cards);
    }

    public Card getCard(int index) {
        if (index >= 5) {
            return null;
        }
        return cards[index];
    }

    public HandCategory getHandCategory() {
        return this.category;
    }

    public Integer getHandValue() {
        return this.handValue;
    }

    public void evaluate() {
        if (this.allSameSuit() != -1 && this.straight() != -1) {
            if (this.getCard(0).getValue() == 10) {
                this.category = HandCategory.ROYAL_FLUSH;
                this.handValue = 9999;
                return;
            } else {
                this.category = HandCategory.STRAIGHT_FLUSH;
                return;
            }
        }

        if (this.four() != -1) {
            this.category = HandCategory.FOUR_OF_A_KIND;
            return;
        }

        if (this.fullHouse() != -1) {
            this.category = HandCategory.FULL_HOUSE;
            return;
        }

        if (this.allSameSuit() != -1) {
            this.category = HandCategory.FLUSH;
            return;
        }

        if (this.straight() != -1) {
            this.category = HandCategory.STRAIGHT;
            return;
        }

        if (this.three() != -1) {
            this.category = HandCategory.THREE_OF_A_KIND;
            return;
        }

        if (this.twoPairs() != -1) {
            this.category = HandCategory.TWO_PAIRS;
            return;
        }

        if (this.pair() != -1) {
            this.category = HandCategory.ONE_PAIR;
            return;
        }

        this.handValue = this.getCard(4).getValue();
        this.category = HandCategory.HIGH_CARD;
    }

    private int pair() {
        int total = 0;
        int noOfCards = 1;
        int previous = this.cards[4].getValue();

        for (int i = 3; i >= 0; i--) {
            if (this.cards[i].getValue() == previous) {
                total += this.cards[i].getValue();
                noOfCards++;
            }

            if (noOfCards == 2) {
                break;
            }
            previous = this.cards[i].getValue();
        }

        if (noOfCards == 2) {
            this.handValue = total;
            return total;
        }
        return -1;
    }

    private int twoPairs() {
        int i = 3;
        int total = 0;
        int noOfCards = 1;
        int previous = this.cards[4].getValue();

        for (; i >= 0; i--) {
            if (this.cards[i].getValue() == previous) {
                total += this.cards[i].getValue();
                noOfCards++;
            }

            if (noOfCards == 2) {
                break;
            } else {
                total = 0;
                noOfCards = 1;
            }
            previous = this.cards[i].getValue();
        }

        if (noOfCards == 2 && i > 0) {
            noOfCards = 1;
            previous = this.cards[i - 1].getValue();
            
            for (i = i - 2; i >= 0; i--) {
                if (this.cards[i].getValue() == previous) {
                    total += this.cards[i].getValue();
                    noOfCards++;
                }
                if (noOfCards == 2) {
                    break;
                } else {
                    total = 0;
                    noOfCards = 1;
                }
                previous = this.cards[i].getValue();
            }
        } else {
            return -1;
        }

        if (noOfCards == 2) {
            this.handValue = total;
            return total;
        }
        return -1;
    }

    private int three() {
        int total = 0;
        int noOfCards = 1;
        int previous = this.cards[4].getValue();

        for (int i = 3; i >= 0; i--) {
            if (this.cards[i].getValue() == previous) {
                total += this.cards[i].getValue();
                noOfCards++;
            } else {
                total = 0;
                noOfCards = 1;
            }

            previous = this.cards[i].getValue();
        }

        if (noOfCards == 3) {
            this.handValue = total;
            return total;
        }
        return -1;
    }

    private int fullHouse() {
        boolean changed = false;
        int total = 0;
        int noOfCards = 1;
        int previous = this.cards[4].getValue();

        for (int i = 3; i >= 0; i--) {
            if (this.cards[i].getValue() == previous) {
                total += this.cards[i].getValue();
                noOfCards++;

            } else if (changed == false) {
                changed = true;
                if (noOfCards < 2) {
                    this.handValue = -1;
                    return -1;
                }

                if (noOfCards == 3) {
                    this.handValue = total;
                }
            } else {
                this.handValue = -1;
                return -1;
            }
            previous = this.cards[i].getValue();
        }
        
        this.handValue = total;
        return total;
    }

    private int four() {
        int total = 0;
        int noOfCards = 1;
        int previous = this.cards[4].getValue();

        for (int i = 3; i >= 0 && noOfCards < 4; i--) {
            if (this.cards[i].getValue() == previous) {
                total += this.cards[i].getValue();
                noOfCards++;
            } else {
                total = 0;
                noOfCards = 1;
            }
            previous = this.cards[i].getValue();
        }

        if (noOfCards == 4) {
            this.handValue = total;
            return total;
        }
        
        return -1;
    }

    private int allSameSuit() {
        char previous = this.cards[0].getSuit();
        int total = this.cards[0].getValue();

        for (int i = 1; i < 5; i++) {
            if (this.cards[i].getSuit() != previous) {
                return -1;
            }
            total += this.cards[i].getValue();
            previous = this.cards[i].getSuit();
        }
        
        this.handValue = total;
        return total;
    }

    private int straight() {
        int previous = this.cards[0].getValue();
        int total = previous;
        
        for (int i = 1; i < 5; i++) {
            if (this.cards[i].getValue() != previous + 1) {
                return -1;
            }
            previous = this.cards[i].getValue();
            total += 1;
        }
        
        this.handValue = total;
        return total;
    }
}
