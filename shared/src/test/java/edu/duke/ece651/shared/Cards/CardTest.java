package edu.duke.ece651.shared.Cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getCardType() {
        Card card = new Card(1);
        card.getCardType();
    }
}