package edu.duke.ece651.shared;

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
        //TODO TEST
        System.out.println("______________________________________________________");
        System.out.println("DO Action, from: " + this.getFrom() + " to: " + this.getTo());
        for(Integer i: getUnitNumber().keySet()){
            System.out.println("Level: " + i + " Nums: " + getUnitNumber().get(i));
        }
        System.out.println("______________________________________________________");
    }

}
