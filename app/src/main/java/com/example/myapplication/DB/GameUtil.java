package com.example.myapplication.DB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameUtil {

    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    static class Card {
        private final Suit mSuit;
        private final Rank mRank;

        Card(Suit suit, Rank rank) {
            this.mSuit = suit;
            this.mRank = rank;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "Suit=" + mSuit +
                    ", Rank=" + mRank +
                    '}';
        }

        public Suit getSuit() {
            return mSuit;
        }

        public Rank getRank() {
            return mRank;
        }
    }
        private final List<Card> deck = new ArrayList<>();
        private final Map<Rank, String> clubMap = new HashMap<>();
        private final Map<Suit, String> shotTypeMap = new HashMap<>();

        public void createDeck() {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    deck.add(new Card(suit, rank));
                }
            }
            initMaps();
        }


        private void initMaps() {
            clubMap.put(Rank.ACE, "Driver");
            clubMap.put(Rank.KING, "Fairway Wood");
            clubMap.put(Rank.TWO, "Fairway Wood/Hybrid");
            clubMap.put(Rank.THREE, "Hybrid/3 Iron");
            clubMap.put(Rank.FOUR, "4 Iron");
            clubMap.put(Rank.FIVE, "5 Iron");
            clubMap.put(Rank.SIX, "6 Iron");
            clubMap.put(Rank.SEVEN, "7 Iron");
            clubMap.put(Rank.EIGHT, "8 Iron");
            clubMap.put(Rank.NINE, "9 Iron");
            clubMap.put(Rank.TEN, "Pitching Wedge");
            clubMap.put(Rank.JACK, "Gap Wedge");
            clubMap.put(Rank.QUEEN, "Lob Wedge");

            shotTypeMap.put(Suit.HEARTS, "Straight");
            shotTypeMap.put(Suit.DIAMONDS, "Fade");
            shotTypeMap.put(Suit.CLUBS, "3/4");
            shotTypeMap.put(Suit.SPADES, "Draw");
        }

        public void shuffleDeck() {
            Collections.shuffle(deck);
        }

        public String getCard() {
            Random random = new Random();
            Card myCard = deck.get(random.nextInt(deck.size()));
            String club = clubMap.get(myCard.getRank());
            String shotType = shotTypeMap.get(myCard.getSuit());
            return "Club: " + club + "\nShot Type: " + shotType + "\n" + myCard.toString();
        }
        public static void main(String[] args) {
            GameUtil game = new GameUtil();
            game.createDeck();
            game.shuffleDeck();
            System.out.println("Drawn Card: " + game.getCard());
        }

    }

