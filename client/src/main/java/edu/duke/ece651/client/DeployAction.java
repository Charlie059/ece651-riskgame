package edu.duke.ece651.client;

import edu.duke.ece651.shared.Territory;

import java.util.HashMap;

public class DeployAction extends Action{
    /**
     * Define the constructor of the Action
     *
     * @param to    Territory
     * @param unitMap HashMap<Integer, Integer> indicate level and numbers
     */
    public DeployAction(Territory to, HashMap<Integer, Integer> unitMap) {
        super(null, to, unitMap, "deploy");
    }

    /**
     * Perform do action
     * @throws IllegalArgumentException if not pass the checker
     */
    @Override
    public void doAction() throws IllegalArgumentException{

    }

}
