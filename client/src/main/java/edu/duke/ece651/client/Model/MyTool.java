package edu.duke.ece651.client.Model;


import javafx.beans.property.SimpleIntegerProperty;

/*
    This class is used to save a kind of tool that own by one player
* */
public class MyTool {
    String ToolName;

    public MyTool(String toolName){
        this.ToolName = toolName;
    }

    public String getToolName() {
        return ToolName;
    }

}
