package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

import java.util.UUID;

public class SpyMoveAction implements Action {
    private UUID spyUUID;
    private String from;
    private String to;

    public SpyMoveAction(UUID spyUUID, String from, String to) {
        this.spyUUID = spyUUID;
        this.from = from;
        this.to = to;
    }

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public String getFrom() {
        return from;
    }

    public UUID getSpyUUID() {
        return spyUUID;
    }

    public String getTo() {
        return to;
    }
}
