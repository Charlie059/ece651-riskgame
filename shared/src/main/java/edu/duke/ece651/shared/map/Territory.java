package edu.duke.ece651.shared.map;

import edu.duke.ece651.shared.Wrapper.AccountID;

import java.util.ArrayList;
import java.util.HashMap;

public class Territory implements Comparable<Territory> {
    private String name;
    private AccountID accountId;
    private int cost;
    private ArrayList<Unit> Units;// key: 7 levels, value: num of units of each level
    private ArrayList<Territory> neighbours;
    private String prev;
    private int dist;


    // create an isolated territory
    public Territory(String name) {
        this.name = name;
        this.accountId = null;
        this.neighbours = new ArrayList<>();
        this.Units = new ArrayList<>();
        //add level 0-6 to units list
        for(int i = 0; i <= 6; i++){
            Units.add(new Unit().setLevel(i).setValue(0));
        }
        this.cost = 10;//default cost: 10
        this.prev = null;
        this.dist = Integer.MAX_VALUE;
    }

    //create a territory with neighbors
    public Territory(String name, ArrayList<Territory> neighbourList) {
        this(name);
        this.neighbours = neighbourList;
    }
    //create a territory with cost value
    public Territory(String name, int cost) {
        this(name);
        this.cost = cost;
    }

    public ArrayList<Territory> getNeighbour() {
        return neighbours;
    }

    public void addNeighbour(Territory t) {
        neighbours.add(t);
    }

    public String getName() {
        return name;
    }

    public void setOwner(AccountID accountId) {
        this.accountId = accountId;
    }

    public AccountID getOwnerId() {
        if (accountId==null){
            return null;
        }else return accountId;}

    public ArrayList<Unit> getUnits() {
        return Units;
    }

    public int getCost(){return cost;}

    public void setCost(int cost){this.cost = cost;}

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    @Override
    public int compareTo(Territory o) {
        return this.dist - o.dist;//min-heap (ascending order)
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

    public void changeOwner(AccountID accountid) {
        if (!this.accountId.equals(accountid)) {
            this.accountId = accountId;
        }
    }

}