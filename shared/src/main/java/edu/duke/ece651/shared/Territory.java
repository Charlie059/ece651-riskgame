package edu.duke.ece651.shared;

import java.util.ArrayList;


public class Territory {
    private String name;
    private int ownerId;
    private ArrayList<Unit> Units;// 6 level, num of units of 6 level's
    private ArrayList<Territory> neighbours;

    public ArrayList<Territory> getNeighbour() {
        return neighbours;
    }


    // create an isolated territory
    public Territory(String name) {
        this.name = name;
        this.name = name;
        this.ownerId = -1;
        this.neighbours = new ArrayList<Territory>();
        this.Units = new ArrayList<>();
        //add level 0-6 to units list
        for(int i = 0; i <= 6; i++){
            Units.add(new Unit().setLevel(i).setValue(0));
        }
    }

    //create a territory with neighbors
    public Territory(String name, ArrayList<Territory> neighbourList) {
        this(name);
        this.neighbours = neighbourList;
    }

    public void addNeighbour(Territory t) {
        neighbours.add(t);
    }

    public String getName() {
        return name;
    }

    public void setOwner(int playerId) {
        ownerId = playerId;
    }

    public int getOwnerId() {return ownerId;}

    public ArrayList<Unit> getUnits() {
        return Units;
    }

    /**
     * add num level-l units
     * @param l : level
     * @param num : number
     * @return true on success, false on failure
     */
    public boolean addUnitLevel(int l, int num, ArrayList<Unit> U) {
        if (l >= 0 && l <= 6){
            int old_val = U.get(l).getValue();
            U.get(l).setValue(old_val + num);
            return true;
        }
        return false;
    }

    /**
     * add multiple levels of units
     * @param arr: 2-D (n*2) array, arr[i][0] = level, arr[i][1] = num
     */
    public boolean addUnitMultiLevels(ArrayList<ArrayList<Integer>> arr) {
        ArrayList<Unit> U =(ArrayList<Unit>) Units.clone();
        for(int i = 0; i < arr.size(); i++){
            int level = arr.get(i).get(0);
            int num = arr.get(i).get(1);
            boolean curr = addUnitLevel(level, num, U);
            if (!curr){
                return false;
            }
        }
        Units = (ArrayList<Unit>) U.clone();
        return true;
    }

    /**
     * remove num level-l units from territory
     * set 0 if num exceeds old num
     * @param l
     * @param num
     * @return true on success, false on failure
     */
    public boolean removeUnitLevel(int l, int num, ArrayList<Unit> U) {
        if (l >= 0 && l <= 6){
            int old_val = U.get(l).getValue();
            if (num <= old_val) {
                U.get(l).setValue(old_val - num);
                return true;
            }
            else{
                U.get(l).setValue(0);
                return false;
            }
        }
        return false;
    }

    /**
     * remove multiple levels of units
     * @param arr: 2-D (n*2) array, arr[i][0] = level, arr[i][1] = num
     * @return true on success, false on failure
     */
    public boolean removeUnitMultiLevels(ArrayList<ArrayList<Integer>> arr) {
        ArrayList<Unit> U =(ArrayList<Unit>) Units.clone();
        for(int i = 0; i < arr.size(); i++){
            int level = arr.get(i).get(0);
            int num = arr.get(i).get(1);

            boolean curr = removeUnitLevel(level, num, U);
            if (curr == false){
                return false;
            }
        }
        Units = (ArrayList<Unit>) U.clone();
        return true;
    }

    public void changeOwner(int player_id) {
        if (player_id != ownerId) {
            ownerId = player_id;
        }
    }

}
