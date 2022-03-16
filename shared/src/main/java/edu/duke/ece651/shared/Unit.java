package edu.duke.ece651.shared;

public class Unit {
    private int level;
    private int hp;
    private int atk;
    public Unit(){
        level = 1;
        hp = 1;
        atk = 1;
    }
    public Unit(int _level){
        level = _level;
        hp = 1;
        atk = 1;
    }
    public int getAtk() {
        return atk;
    }
    public int getHp() {
        return hp;
    }
    public int getLevel() {
        return level;
    }
}
