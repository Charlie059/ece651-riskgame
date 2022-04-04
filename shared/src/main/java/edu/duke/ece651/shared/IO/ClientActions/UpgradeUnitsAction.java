package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class UpgradeUnitsAction implements Action {
    private String where;//which territory to upgrade
    private int oldLevel;
    private int newLevel;
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public int getOldLevel(){
        return oldLevel;
    }

    public UpgradeUnitsAction setOldLevel(int oldLevel){
        this.oldLevel = oldLevel;
        return this;
    }

    public int getNewLevel(){
        return newLevel;
    }

    public UpgradeUnitsAction setNewLevel(int newLevel){
        this.newLevel = newLevel;
        return this;
    }

    public String getWhere(){return where;}

    public UpgradeUnitsAction setWhere(String _where){
        where= _where;
        return this;
    }

}
