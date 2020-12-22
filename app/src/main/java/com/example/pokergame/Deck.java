package com.example.pokergame;
import java.util.ArrayList;

public class Deck
{
    private ArrayList<Card> cards=new ArrayList<Card>();

    public Deck() {
        cards.add(new Card(Rank.Two, Suit.Heart, R.drawable.poker1));
        cards.add(new Card(Rank.Two, Suit.Spade, R.drawable.poker2));
        cards.add(new Card(Rank.Two, Suit.Club, R.drawable.poker3));
        cards.add(new Card(Rank.Five, Suit.Heart, R.drawable.poker4));
        cards.add(new Card(Rank.Five, Suit.Spade, R.drawable.poker5));
        cards.add(new Card(Rank.Two, Suit.Diamond, R.drawable.poker6));
        cards.add(new Card(Rank.Ten, Suit.Heart, R.drawable.poker7));
        cards.add(new Card(Rank.Ten, Suit.Spade, R.drawable.poker8));
        cards.add(new Card(Rank.Three, Suit.Club, R.drawable.poker9));
        cards.add(new Card(Rank.Three, Suit.Diamond, R.drawable.poker10));
        cards.add(new Card(Rank.Three, Suit.Heart, R.drawable.poker11));
        cards.add(new Card(Rank.Three, Suit.Spade, R.drawable.poker12));
        cards.add(new Card(Rank.Four, Suit.Club, R.drawable.poker13));
        cards.add(new Card(Rank.Four, Suit.Diamond, R.drawable.poker14));
        cards.add(new Card(Rank.Four, Suit.Heart, R.drawable.poker15));
        cards.add(new Card(Rank.Four, Suit.Spade, R.drawable.poker16));
        cards.add(new Card(Rank.Five, Suit.Club, R.drawable.poker17));
        cards.add(new Card(Rank.Five, Suit.Diamond, R.drawable.poker18));
        cards.add(new Card(Rank.Six, Suit.Diamond, R.drawable.poker19));
        cards.add(new Card(Rank.Six, Suit.Club, R.drawable.poker20));
        cards.add(new Card(Rank.Six, Suit.Spade, R.drawable.poker21));
        cards.add(new Card(Rank.Six, Suit.Heart, R.drawable.poker22));
        cards.add(new Card(Rank.Seven, Suit.Heart, R.drawable.poker23));
        cards.add(new Card(Rank.Seven, Suit.Spade, R.drawable.poker24));
        cards.add(new Card(Rank.Seven, Suit.Club, R.drawable.poker25));
        cards.add(new Card(Rank.Seven, Suit.Diamond, R.drawable.poker26));
        cards.add(new Card(Rank.Eight, Suit.Club, R.drawable.poker27));
        cards.add(new Card(Rank.Eight, Suit.Diamond, R.drawable.poker28));
        cards.add(new Card(Rank.Eight, Suit.Heart, R.drawable.poker29));
        cards.add(new Card(Rank.Eight, Suit.Spade, R.drawable.poker30));
        cards.add(new Card(Rank.Nine, Suit.Club, R.drawable.poker31));
        cards.add(new Card(Rank.Nine, Suit.Diamond, R.drawable.poker32));
        cards.add(new Card(Rank.Nine, Suit.Heart, R.drawable.poker33));
        cards.add(new Card(Rank.Nine, Suit.Spade, R.drawable.poker34));
        cards.add(new Card(Rank.Ten, Suit.Diamond, R.drawable.poker35));
        cards.add(new Card(Rank.Ten, Suit.Club, R.drawable.poker36));
        cards.add(new Card(Rank.Jack, Suit.Heart, R.drawable.poker37));
        cards.add(new Card(Rank.Jack, Suit.Spade, R.drawable.poker38));
        cards.add(new Card(Rank.Jack, Suit.Club, R.drawable.poker39));
        cards.add(new Card(Rank.Jack, Suit.Diamond, R.drawable.poker40));
        cards.add(new Card(Rank.Queen, Suit.Heart, R.drawable.poker41));
        cards.add(new Card(Rank.Queen, Suit.Spade, R.drawable.poker42));
        cards.add(new Card(Rank.Queen, Suit.Club, R.drawable.poker43));
        cards.add(new Card(Rank.Queen, Suit.Diamond, R.drawable.poker44));
        cards.add(new Card(Rank.King, Suit.Club, R.drawable.poker45));
        cards.add(new Card(Rank.King, Suit.Diamond, R.drawable.poker46));
        cards.add(new Card(Rank.King, Suit.Heart, R.drawable.poker47));
        cards.add(new Card(Rank.King, Suit.Spade, R.drawable.poker48));
        cards.add(new Card(Rank.Ace, Suit.Club, R.drawable.poker49));
        cards.add(new Card(Rank.Ace, Suit.Diamond, R.drawable.poker50));
        cards.add(new Card(Rank.Ace, Suit.Heart, R.drawable.poker51));
        cards.add(new Card(Rank.Ace, Suit.Spade, R.drawable.poker52));
    }

    public ArrayList<Card> getCards(){
        return this.cards;
    }



}
