package edu.duke.ece651.server;

import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.AttackAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;
import edu.duke.ece651.shared.map.Unit;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class CombatResolution {
    private volatile GameHashMap gameHashMap;
    private volatile GameID gameID;
    private volatile ArrayList<ArrayList<Integer>> temp_currUnitList;//<index, [Level,Value]>
    private HashMap<String, ArrayList<ArrayList<Integer>>> attackUnitListHashMap;//<To, [level,value]>
    public CombatResolution(GameHashMap gameHashMap, GameID gameID) {
        this.gameHashMap = gameHashMap;
        this.gameID = gameID;
        this.temp_currUnitList = new ArrayList<>();
    }

    class arrayListIntegerComparator implements Comparator<ArrayList<Integer>>{

        @Override
        public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
            if(o1.get(0)>o2.get(0)){
                return -1;//left before right
            }else if(o1.get(0)==o2.get(0)){
                return 0;//stay the same
            }
            else return 1;//right before left
        }
    }

    public void singlePadMergeAndSort(ArrayList<ArrayList<Integer>> currUnitList){
        Collections.sort(currUnitList,new arrayListIntegerComparator());

        ArrayList<ArrayList<Integer>> newUnitList = new ArrayList<>();
        int levelCounter = 6;
        //Go through uncompleted sorted list
        for(int i = 0; i< currUnitList.size(); i++){

            //padding
            //if larger level is missing, create new [level, 0]
            while (currUnitList.get(i).get(0) < levelCounter){
                ArrayList<Integer> arr = new ArrayList<>();
                arr.add(levelCounter);
                arr.add(0);
                newUnitList.add(arr);
                levelCounter--;
            }
            //If Do not need Padding
            if(currUnitList.get(i).get(0) == levelCounter){
                // if curr has next and levels are same Merging
                if (i < currUnitList.size()-1 && currUnitList.get(i).get(0).equals(currUnitList.get(i+1).get(0))){
                    Integer value = currUnitList.get(i).get(1);
                    Integer level = 0;
                    //continue merging if next of next has the same level
                    while (i < currUnitList.size() - 1 && currUnitList.get(i).get(0).equals(currUnitList.get(i + 1).get(0))) {
                        level = currUnitList.get(i).get(0);//level = curr level
                        value += currUnitList.get(i + 1).get(1); //value = sum of curr and next
                        i++;//skip next(same level)
                    }
                    ArrayList<Integer> arr = new ArrayList<>();
                    arr.add(level);//level = curr level
                    arr.add(value);
                    newUnitList.add(arr);
                }
                else{//if curr does not have next or curr level and next level are diff, don't merge
                    ArrayList<Integer> arr = new ArrayList<>();
                    arr.add(currUnitList.get(i).get(0));//level = curr level
                    arr.add(currUnitList.get(i).get(1));//value = curr value
                    newUnitList.add(arr);
                }
                levelCounter--;
            }
        }
        this.temp_currUnitList = (ArrayList<ArrayList<Integer>>) newUnitList.clone();
    }



    public ArrayList<ArrayList<Integer>> getTemp_currUnitList() {
        return temp_currUnitList;
    }

    public HashMap<String, ArrayList<ArrayList<Integer>>> getAttackUnitListHashMap() {
        return attackUnitListHashMap;
    }

    public void combatResolution(Integer diceDebugMode) {
        //One Attack
        //Game->AttackHashMap:<AccountID, ArrayList<AttackActions>>
        //Naturally Randomly when iterate this HashMap, to decide the order of attack the same "to"
        for (AccountID key : this.gameHashMap.get(this.gameID).getAttackHashMap().getAttackHashmap().keySet()) {
            ArrayList<AttackAction> attackActionArrayList = this.gameHashMap.get(this.gameID).getAttackHashMap().getAttackHashmap().get(key);
            //FIND AND MERGE SAME TO UNITS
            this.attackUnitListHashMap = new HashMap<>();//reset attackunitHashMap for new accountID
            for (AttackAction attackAction : attackActionArrayList){
                //If To is new case, put this case to HashMap
                if(!this.attackUnitListHashMap.containsKey(attackAction.getTo())){
                    this.singlePadMergeAndSort(attackAction.getUnits());
                    this.attackUnitListHashMap.put(attackAction.getTo(), (ArrayList<ArrayList<Integer>>) this.temp_currUnitList.clone());
                }else {
                    //If To is existed, merge
                    this.singlePadMergeAndSort(attackAction.getUnits());
                    ArrayList<ArrayList<Integer>> oldUnitList = this.attackUnitListHashMap.get(attackAction.getTo());
                    ArrayList<ArrayList<Integer>> mergedUnitList = new ArrayList<>();
                    assert(oldUnitList.size()==this.temp_currUnitList.size());
                    for(int i = 0; i<oldUnitList.size();i++){
                        ArrayList<Integer> temp = new ArrayList<>();
                        temp.add(this.temp_currUnitList.get(i).get(0));//Level
                        temp.add(oldUnitList.get(i).get(1)+this.temp_currUnitList.get(i).get(1));// value_old + value_new
                        mergedUnitList.add(temp);
                    }
                    this.attackUnitListHashMap.replace(attackAction.getTo(),mergedUnitList);

                }
            }
            //do AllAttacks
            doAllAttacks(key, this.attackUnitListHashMap, diceDebugMode);
        }

    }

    /**
     * this attacker implements all of her attack action in serial
     * @param accountID attacker's id
     * @param attackUnitListHashMap <String terrToName, Arr<Arr<Int>>>
     * @param diceDebugMode if debugmode is 1, attacker always win; if -1, defender always win; if 0, random
     */
    public void doAllAttacks(AccountID accountID,
                             HashMap<String, ArrayList<ArrayList<Integer>>> attackUnitListHashMap,
                             Integer diceDebugMode){
        //go through territory to

        for(String terrToAttack: attackUnitListHashMap.keySet()){
            //find Territory to
            Territory attackTerr = this.gameHashMap.get(this.gameID).getMap().getTerritoryList().get(terrToAttack);
            //find defender's units
            ArrayList<Unit> defenderUnits = attackTerr.getUnits();
            //do attack
            attackP2P(accountID, attackTerr, attackUnitListHashMap.get(terrToAttack), defenderUnits, diceDebugMode);
        }
    }

    /**
     * attack happens on territory attackTerr between an attacker and a defender (P2P)
     * @param attackerID attacker's id
     * @param attackTerr territory to attack
     * @param attackerUnits in descending order, padding: ((6,1) (5, 0) (4, 1) (3, 0) (2, 0) (1, 1) (0, 1))
     * @param defenderUnits in ascending order, padding ((0, 0) (1, 0) (2, 1) (3, 0) (4, 2) (5, 0) (6, 0))
     * @param diceDebugMode if debugmode is 1, attacker always win; if -1, defender always win; if 0, random
     */
    public void attackP2P(AccountID attackerID,
                               Territory attackTerr,
                               ArrayList<ArrayList<Integer>> attackerUnits,
                               ArrayList<Unit> defenderUnits,
                               Integer diceDebugMode){
        //TWO POINTER method:
        //initialize attackUnits' pointers: atkH->highest level index, atkL->lowest level index
        int atkH = 0;
        int atkL = attackerUnits.size()-1;
        //initialize defenderUnits' pointers: dfnH->highest level index, dfnL->lowest level index
        int dfnH = defenderUnits.size()-1;
        int dfnL = 0;
        //iterate attackUnits, play multiple rounds
        while(atkH <= atkL){
            if (dfnL > dfnH){ // if defender lose
                //change ownership, reset units
                attackTerr.changeOwner(attackerID);
                attackTerr.addUnitMultiLevels(attackerUnits);
                return;
            }
            //PHASE 1: highest-bonus attacker unit paired with the lowest-bonus defender unit
            //attack: skip empty level
            while(attackerUnits.get(atkH).get(1) == 0 && atkH< attackerUnits.size()-1){atkH++;}
            if (atkH <= atkL){
                Integer attackLevel = attackerUnits.get(atkH).get(0);
                //defender: skip empty level
                while(defenderUnits.get(dfnL).getValue() == 0 && dfnL < defenderUnits.size()-1){dfnL++;}
                if (dfnL <= dfnH && dfnL < defenderUnits.size()-1){
                    Integer defendLevel = defenderUnits.get(dfnL).getLevel();
                    //implement level by level attack
                    attackLevelByLevel(attackLevel, defendLevel, attackerUnits.get(atkH), defenderUnits.get(dfnL), diceDebugMode);
                }
            }
            //PHASE 2: then, lowest-bonus attacker unit paired with the highest-bonus defender unit
            //skip empty level
            while(attackerUnits.get(atkL).get(1) == 0 && atkL > 0){atkL--;}
            if (atkH <= atkL){
                Integer attackLevel = attackerUnits.get(atkL).get(0);
                //defender: skip empty level
                while(defenderUnits.get(dfnH).getValue() == 0 && dfnH > 0){dfnH--;}
                if (dfnL <= dfnH){
                    Integer defendLevel = defenderUnits.get(dfnH).getLevel();
                    //implement level by level attack
                    attackLevelByLevel(attackLevel, defendLevel, attackerUnits.get(atkL), defenderUnits.get(dfnH), diceDebugMode);
                }
            }
        }
        //if attacker lose, do nothing
    }

    /**
     * attack happens between one level of attackUnit and one level of defendUnit
     * @param attackLevel: attackUnits' current level
     * @param defendLevel: defendUnits' current level
     * @param attackLevelUnit: attackUnits of current level
     * @param defendLevelUnit: defendUnits of current level
     * @param diceDebugMode if debugmode is 1, attacker always win; if -1, defender always win; if 0, random
     */
    public void attackLevelByLevel(Integer attackLevel,
                                   Integer defendLevel,
                                   ArrayList<Integer> attackLevelUnit,
                                   Unit defendLevelUnit,
                                   Integer diceDebugMode
                                   ){
        //implement attack
        Dice dice;
        if (diceDebugMode == 0) {
             dice = new Dice(attackLevel, defendLevel);
        }
        else{
            dice = new Dice(attackLevel, defendLevel, diceDebugMode);
        }
        ArrayList<Integer> result = dice.getResult();
        if (result.get(0) > result.get(1)){//attack wins this round
            //defender loses one unit
            defendLevelUnit.setValue(defendLevelUnit.getValue()-1);
        }
        else{//defender wins this round
            //attacker loses one unit
            attackLevelUnit.set(1,  attackLevelUnit.get(1)-1);
        }

    }

    //doCombat(DiceDebugMode, )
    public void doCombat(){

    }
    //doCombat(DiceDebugMode)

    /**
     * DiceDebugMode: 0, default; 1, attacker wins; others defender wins
     * @param DiceDebugMode
     */
    public void doCombat(Integer DiceDebugMode){
        combatResolution(DiceDebugMode);
    }


}
