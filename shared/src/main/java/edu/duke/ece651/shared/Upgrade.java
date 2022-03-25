package edu.duke.ece651.shared;

public class Upgrade {
    private String territory;
    private Integer levelFrom;
    private Integer levelTo;
    private Integer numOfUnits;

    /**
     * Constructor of Upgrade
     * @param territory Name of territory that units belongs to
     * @param levelFrom Unit original level
     * @param levelTo Unit upgrade level to
     * @param numOfUnits How many of these types of units
     */
    public Upgrade(String territory, Integer levelFrom, Integer levelTo, Integer numOfUnits) {
        this.territory = territory;
        this.levelFrom = levelFrom;
        this.levelTo = levelTo;
        this.numOfUnits = numOfUnits;
    }

    public String getTerritory() {
        return territory;
    }

    public Integer getLevelFrom() {
        return levelFrom;
    }

    public Integer getLevelTo() {
        return levelTo;
    }

    public Integer getNumOfUnits() {
        return numOfUnits;
    }
}
