package com.poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author vasant
 */
@Slf4j
public class PlayPoker {

    public static void main(String[] args) {
        try {
            int player1 = 0;
            int player2 = 0;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String input = br.readLine();

                if (input == null) {
                    break;
                }
                if (!input.matches("(?:[2-9TJQKA][SCHD] ){9}[2-9TJQKA][SCHD]")) {
                    log.info("Wrong input provided");
                    break;
                }

                String[] cards = input.split(" ");
                String[] handOneString = Arrays.copyOfRange(cards, 0, 5);
                String[] handTwoString = Arrays.copyOfRange(cards, 5, 10);

                Hand handOne = new Hand(handOneString);
                Hand handTwo = new Hand(handTwoString);

                handOne.sortCards();
                handTwo.sortCards();

                handOne.evaluate();
                handTwo.evaluate();

                switch (getWinner(handOne, handTwo)) {
                    case 1:
                        player1++;
                        break;
                    case 2:
                        player2++;
                        break;
                    default:
                        log.info("It is a tie");
                        break;
                }
            }

            log.info("Player 1: " + player1 + " hands");
            log.info("Player 2: " + player2 + " hands");
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    public static int getWinner(Hand hand1, Hand hand2) {
        if (hand1.getHandCategory().getValue() > hand2.getHandCategory().getValue()) {
            return 1;
        } else if (hand1.getHandCategory().getValue() < hand2.getHandCategory().getValue()) {
            return 2;
        } else if (hand1.getHandValue() > hand2.getHandValue()) {
            return 1;
        } else if (hand1.getHandValue() < hand2.getHandValue()) {
            return 2;
        } else {
            for (int i = 4; i >= 0; i--) {
                if (hand1.getCard(i).getValue() > hand2.getCard(i).getValue()) {
                    return 1;
                } else if (hand1.getCard(i).getValue() < hand2.getCard(i).getValue()) {
                    return 2;
                }
            }
            return -1;
        }
    }
}
