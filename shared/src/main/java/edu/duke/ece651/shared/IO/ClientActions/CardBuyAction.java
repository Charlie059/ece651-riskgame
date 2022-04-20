package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

import java.util.List;

public class CardBuyAction implements Action{
    private List<Integer> cardType;

    public CardBuyAction(List<Integer> cardType) {
        this.cardType = cardType;
    }


    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public List<Integer> getCardType() {
        return cardType;
    }
}
