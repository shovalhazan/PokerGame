package com.example.pokergame;

//This enum will represent the card values.
//Ace is set to 1 so the enum storage matches
//the card rank.
enum Rank
{
    Two,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,
    Jack,
    Queen,
    King,
    Ace
}
//Standard enum for card suits.
enum Suit
{
    Club,
    Diamond,
    Heart,
    Spade
}
public class Card {
    private int card_img_id;
    private  Suit suit;
    private Rank rank;

    public Card(Rank rank,Suit suit , int id){
        this.rank =rank;
        this.suit=suit;
        card_img_id=id;
    }
    public int getCard_img_id(){
        return card_img_id;
    }

    public Rank getRank(){
        return rank;
    }




}
