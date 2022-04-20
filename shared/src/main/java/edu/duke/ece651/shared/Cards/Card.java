package edu.duke.ece651.shared.Cards;

/**
 * The Card the Player will have
 */
public class Card {
private Integer cardType;
        
    public Card(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getCardType() {
        return cardType;
    }
}
