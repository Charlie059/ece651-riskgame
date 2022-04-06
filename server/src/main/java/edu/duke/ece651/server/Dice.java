package edu.duke.ece651.server;

import edu.duke.ece651.shared.map.Unit;

import java.util.ArrayList;
import java.util.Random;

public class Dice {
    private Random random;
    private ArrayList<Integer> Result;// 2-d result of 2 unit
    private Boolean debugMode = false;//random.nextInt(20)+1
    private ArrayList<Integer> bonusTable;
    private Integer attackUnitLevel;
    private Integer defenderUnitLevel;

    private void setBonusTable(){
        this.bonusTable = new ArrayList<>();
        this.bonusTable.add(0);//level0
        this.bonusTable.add(1);//level1
        this.bonusTable.add(3);//level2
        this.bonusTable.add(5);//level3
        this.bonusTable.add(8);//level4
        this.bonusTable.add(11);//level5
        this.bonusTable.add(15);//level6
    }

    /**
     * Calculate the dice result
     * ***Note for Debug Mode:
     * if debug is on, dice will force add 100 extra bonus to attacker to make it always win
     */
    private void doDice(){
        Integer debugBonus = this.debugMode?100:0;
        Integer attackerResult = this.random.nextInt(20)+this.bonusTable.get(this.attackUnitLevel)+debugBonus;
        Integer defenderResult = this.random.nextInt(20)+this.bonusTable.get(this.defenderUnitLevel);
        this.Result.add(attackerResult);
        this.Result.add(defenderResult);
    }

    /**
     * Constructor of Dice without debug mode
     * @param attackUnitLevel attacker unit level
     * @param defenderUnitLevel defender unit level
     */
    public Dice(Integer attackUnitLevel, Integer defenderUnitLevel) {
        this.attackUnitLevel = attackUnitLevel;
        this.defenderUnitLevel = defenderUnitLevel;
        this.random = new Random();
        this.Result = new ArrayList<>();
        this.setBonusTable();
        this.doDice();
    }

    /**
     * Constructor of Dice with debug mode
     * @param attackUnitLevel attacker unit level
     * @param defenderUnitLevel defender unit level
     * @param debugMode if debugmode is true, attacker always win
     */
    public Dice(Integer attackUnitLevel, Integer defenderUnitLevel,Boolean debugMode) {
        this.debugMode = debugMode;
        this.attackUnitLevel = attackUnitLevel;
        this.defenderUnitLevel=defenderUnitLevel;
        this.random = new Random();
        this.Result= new ArrayList<>();
        this.setBonusTable();
        this.doDice();
    }

    public ArrayList<Integer> getResult() {
        return Result;
    }
}
