package edu.duke.ece651.client.Model;

/*
this class is used to save differents kinds of tool
* */
public class SpecialTool {
    String ToolName;
    int Cost;
    String Desctiption;

    public SpecialTool(String name, int cost, String d){
        this.ToolName = name;
        this.Cost = cost;
        this.Desctiption = d;
    }

    public int getCost() {
        return Cost;
    }

    public String getToolName() {
        return ToolName;
    }
}
