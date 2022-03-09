package edu.duke.ece651.client;

import edu.duke.ece651.shared.Territory;

import java.util.HashMap;

public class AttackAction extends Action{

    /**
     * Define the constructor of the Action
     *
     * @param from    Territory
     * @param to      Territory
     * @param unitMap HashMap<Integer, Integer> indicate level and numbers
     */
    public AttackAction(Territory from, Territory to, HashMap<Integer, Integer> unitMap) {
        super(from, to, unitMap, "attack");
    }

    /**
     * Perform do action
     * @throws IllegalArgumentException if not pass the checker
     */
    @Override
    public void doAction() throws IllegalArgumentException{

    }

}
