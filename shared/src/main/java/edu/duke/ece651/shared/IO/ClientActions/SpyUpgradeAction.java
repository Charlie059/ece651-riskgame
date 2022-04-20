package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

import java.util.UUID;

public class SpyUpgradeAction implements Action{
    private String from;
    private UUID spyUUID;
    private Integer type;

    //When Set Type, use new SpyType.<type> rather than pure Integer Number
    //Example : new SpyUpgradeAction(UUID, new SpyType.Rosenbergs)
    public SpyUpgradeAction(String from, UUID spyUUID, Integer type) {
        this.from = from;
        this.spyUUID = spyUUID;
        this.type = type;
    }

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public UUID getSpyUUID() {
        return spyUUID;
    }

    public Integer getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }
}
