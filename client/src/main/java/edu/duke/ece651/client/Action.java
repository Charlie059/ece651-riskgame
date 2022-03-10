package edu.duke.ece651.client;

import edu.duke.ece651.shared.Territory;

import java.util.HashMap;

public abstract class Action {
    private String actionName;
    private Territory from;
    private Territory to;
    private HashMap<Integer, Integer> unitNumber; // level and numbers
    // TODO Client rule checker

    /**
     * Define the constructer of the Action
     * @param from Territory
     * @param to Territory
     * @param unitMap HashMap<Integer, Integer> indicate level and numbers
     */
    public Action(Territory from, Territory to, HashMap<Integer, Integer> unitMap, String actionName){
        this.from = from;
        this.to = to;
        this.unitNumber = unitMap;
        this.actionName = actionName;
    }

    /**
     * Perform do action
     * @throws IllegalArgumentException if not pass the checker
     */
    public abstract void doAction() throws IllegalArgumentException;

    /**
     * Get action name
     * @return action name
     */
    public String getActionName() {
        return actionName;
    }


    public Territory getFrom() {
        return from;
    }

    public Territory getTo() {
        return to;
    }

    public HashMap<Integer, Integer> getUnitNumber() {
        return unitNumber;
    }
}
