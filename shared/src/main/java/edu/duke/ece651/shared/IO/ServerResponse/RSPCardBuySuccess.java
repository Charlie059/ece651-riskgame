package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPCardBuySuccess implements Response{

    private final Integer cardCost;
    private final Integer cardType;

    public RSPCardBuySuccess(Integer cardCost, Integer cardType) {
        this.cardCost = cardCost;
        this.cardType = cardType;
    }

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

    public Integer getCardCost() {
        return cardCost;
    }

    public Integer getCardType() {
        return cardType;
    }
}
