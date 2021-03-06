package com.poker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

@Slf4j
public class PlayPokerTest {

    @Test
    public void testMain() throws FileNotFoundException {
        int player1 = 0;
        int player2 = 0;

        InputStream is = new FileInputStream(new File("src/test/resources/poker-hands.txt"));
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        try {
            while (true) {
                String input = br.readLine();
                if (input == null) {
                    break;
                }
                if (!input.matches("(?:[2-9TJQKA][SCHD] ){9}[2-9TJQKA][SCHD]")) {
                    log.info("Wrong input format.");
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

                switch (PlayPoker.getWinner(handOne, handTwo)) {
                    case 1:
                        player1++;
                        break;
                    case 2:
                        player2++;
                        break;
                    default:
                        log.info("There is a tie!");
                        break;
                }

            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        
        assertEquals("Player 1 has 263 wins", 263, player1);
        log.info("Player 1: 263");
        assertEquals("Player 2 has 237 wins", 237, player2);
        log.info("Player 2: 237");
    }

    @Test
    public void testGetWinner() {
        Hand handOne = new Hand("9C 9D 8D 7C 3C".split(" "));
        Hand handTwo = new Hand("2S KD TH 9H 8H".split(" "));
        handOne.sortCards();
        handTwo.sortCards();
        handOne.evaluate();
        handTwo.evaluate();
        assertEquals("Player 1 wins this game for one pair vs high card", 1, PlayPoker.getWinner(handOne, handTwo));

        handOne = new Hand("TC 2C JC 7C 3C".split(" "));
        handTwo = new Hand("9H 9C 9S 7D 9D".split(" "));
        handOne.sortCards();
        handTwo.sortCards();
        handOne.evaluate();
        handTwo.evaluate();
        assertEquals("Player 2 wins for flush vs four of a kind", 2, PlayPoker.getWinner(handOne, handTwo));

        handOne = new Hand("9C 9D 8D 7C TC".split(" "));
        handTwo = new Hand("9H 9S 8C 7D 3D".split(" "));
        handOne.sortCards();
        handTwo.sortCards();
        handOne.evaluate();
        handTwo.evaluate();
        assertEquals("Player 1 wins for one pair(high card tie-break) vs one pair", 1, PlayPoker.getWinner(handOne, handTwo));

        handOne = new Hand("9C 9D 8D 7C 3C".split(" "));
        handTwo = new Hand("9H 9S 8C 7D 3D".split(" "));
        handOne.sortCards();
        handTwo.sortCards();
        handOne.evaluate();
        handTwo.evaluate();
        assertEquals("This is a tie", -1, PlayPoker.getWinner(handOne, handTwo));

        handOne = new Hand("TC 2C TS 2S TD".split(" "));
        handTwo = new Hand("AH KH JH QH TH".split(" "));
        handOne.sortCards();
        handTwo.sortCards();
        handOne.evaluate();
        handTwo.evaluate();
        assertEquals("Player 2 wins for royal flush vs full house", 2, PlayPoker.getWinner(handOne, handTwo));
    }

}
