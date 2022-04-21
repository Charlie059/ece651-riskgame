package edu.duke.ece651.shared.map;

import edu.duke.ece651.shared.Wrapper.AccountID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Territory implements Comparable<Territory>, Serializable {
    private String name;
    private AccountID accountId;
    private int cost;
    private ArrayList<Unit> Units;// key: 7 levels, value: num of units of each level
    private ArrayList<Territory> neighbours;
    private String prev;
    private int dist;
    private HashSet<Spy> Spys;// type: 3 types, with AccountID

    private ArrayList<Integer> Cloak;//[iscloaked,left turn]

    // create an isolated territory
    public Territory(String name) {
        this.name = name;
        this.accountId = null;
        this.neighbours = new ArrayList<>();
        this.Units = new ArrayList<>();
        this.Spys = new HashSet<>();
        this.Cloak = new ArrayList<>();
        this.Cloak.add(0);
        this.Cloak.add(0);
        //add level 0-6 to units list
        for (int i = 0; i <= 6; i++) {
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

    //add spy
    public void addSpy(Spy spy) {
        this.Spys.add(spy);
    }

    //remove spy
    public void removeSpy(Spy spy) {
        this.Spys.remove(spy);
    }

    //get
    public Spy getSpy(UUID uuid) {
        for (Spy spy : Spys) {
            if (spy.getSpyUUID().equals(uuid)) {
                return spy;
            }
        }
        return null;
    }
    //Get spyInfo of this territory belongs to this person
    public ArrayList<Spy> getSpyInfo(AccountID accountID){
        ArrayList<Spy> spyInfo = new ArrayList<>();
        for(Spy spy: Spys){
            if(spy.getSpyOwnerAccountID().equals(accountID)){
                spyInfo.add(spy);
            }
        }
        return spyInfo;
    }

    //Do I have Spy on this territory
    public Boolean isHaveMySpy(AccountID accountID){
        for(Spy spy: Spys){
            if(spy.getSpyOwnerAccountID().equals(accountID)){
                return true;
            }
        }
        return false;
    }

    public Boolean isEnoughUnitLevelOf(Integer level){
        for(Unit unit: this.Units){
            if(unit.getLevel().equals(level)){
                return true;
            }
        }
        return false;
    }

    public void setCloak(){
        this.Cloak.set(0,1);
        this.Cloak.set(1,3);
    }
    public void updateCloak(){
        this.Cloak.set(1,this.Cloak.get(1)-1);
        if(this.Cloak.get(1)<=0){
            this.Cloak.set(0,0);
        }
    }
    public Boolean isCloaked(){
        if(this.Cloak.get(0).equals(1)){
            return true;
        }
        return false;
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
        if (accountId == null) {
            return null;
        } else return accountId;
    }

    public ArrayList<Unit> getUnits() {
        return Units;
    }

    public void setUnits(ArrayList<Unit> U){this.Units = U;}

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

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
     *
     * @param l   : level
     * @param num : number
     * @return true on success, false on failure
     */
    public boolean addUnitLevel(int l, int num, ArrayList<Unit> U) {
        if (l >= 0 && l <= 6) {
            int old_val = U.get(l).getValue();
            U.get(l).setValue(old_val + num);
            return true;
        }
        return false;
    }


    /**
     * add units by hash map (!!!!make sure this territory is EMPTY!!!!)
     * after combat resolution, if attack wins, add her attack units (in hashmap)
     * to the occupied territory
     *
     * @param unitsHashMap
     */
    public void addUnitMultiLevelsHashMap(HashMap<Integer, Integer> unitsHashMap) {
        ArrayList<Unit> U = new ArrayList<>();
        Unit u0 = new Unit().setLevel(0).setValue(0);
        Unit u1 = new Unit().setLevel(1).setValue(0);
        Unit u2 = new Unit().setLevel(2).setValue(0);
        Unit u3 = new Unit().setLevel(3).setValue(0);
        Unit u4 = new Unit().setLevel(4).setValue(0);
        Unit u5 = new Unit().setLevel(5).setValue(0);
        Unit u6 = new Unit().setLevel(6).setValue(0);
        U.add(u0);
        U.add(u1);
        U.add(u2);
        U.add(u3);
        U.add(u4);
        U.add(u5);
        U.add(u6);
        for (Integer level : unitsHashMap.keySet()) {
            U.get(level).setValue(unitsHashMap.get(level));
        }
        this.Units = U;

    }


    /**
     * add multiple levels of units
     *
     * @param arr: 2-D (n*2) array, arr[i][0] = level, arr[i][1] = num
     */
    public boolean addUnitMultiLevels(ArrayList<ArrayList<Integer>> arr) {
        ArrayList<Unit> U = (ArrayList<Unit>) Units.clone();
        for (int i = 0; i < arr.size(); i++) {
            int level = arr.get(i).get(0);
            int num = arr.get(i).get(1);
            boolean curr = addUnitLevel(level, num, U);
            if (!curr) {
                return false;
            }
        }
        Units = (ArrayList<Unit>) U.clone();
        return true;
    }


    /**
     * remove num level-l units from territory
     * set 0 if num exceeds old num
     *
     * @param l
     * @param num
     * @return true on success, false on failure
     */
    public boolean removeUnitLevel(int l, int num, ArrayList<Unit> U) {
        if (l >= 0 && l <= 6) {
            int old_val = U.get(l).getValue();
            if (num <= old_val) {
                U.get(l).setValue(old_val - num);
                return true;
            } else {
                U.get(l).setValue(0);
                return false;
            }
        }
        return false;
    }

    /**
     * remove multiple levels of units
     *
     * @param arr: 2-D (n*2) array, arr[i][0] = level, arr[i][1] = num
     * @return true on success, false on failure
     */
    public boolean removeUnitMultiLevels(ArrayList<ArrayList<Integer>> arr) {
        ArrayList<Unit> U = (ArrayList<Unit>) Units.clone();
        for (int i = 0; i < arr.size(); i++) {
            int level = arr.get(i).get(0);
            int num = arr.get(i).get(1);

            boolean curr = removeUnitLevel(level, num, U);
            if (curr == false) {
                return false;
            }
        }
        Units = (ArrayList<Unit>) U.clone();
        return true;
    }

    /**
     * remove units of all LVs by half (rounded up)
     */
    public void removeUnitsByHalf(){
        for(Unit u: this.Units){
            u.setValue(u.getValue()/2);
        }
    }

    /**
     *  all units (except Lv6) in territory level up (by 1 level)
     */
    public void unitGreatLeapForward(){
        for(int i = 6; i>=1; i--){
            this.Units.get(i).setValue(
                    this.Units.get(i).getValue() + this.Units.get(i-1).getValue());
            this.Units.get(i-1).setValue(0);
        }
    }

    public void changeOwner(AccountID accountid) {
        if (!this.accountId.equals(accountid)) {
            this.accountId = accountid;
        }
    }


    public boolean isEmpty() {
        for (Unit u : this.getUnits()) {
            if (u.getValue() != 0) {
                return false;
            }
        }
        return true;
    }
}