package edu.duke.ece651.shared;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerJSONParserTest {
    String src = "{\n" +
            "  \"map\": [\n" +
            "    {\n" +
            "      \"name\": \"a1\",\n" +
            "      \"ownerID\": \"1\",\n" +
            "      \"units\": [\n" +
            "        {\n" +
            "          \"level\": 1,\n" +
            "          \"value\": 3\n" +
            "        },\n" +
            "        {\n" +
            "          \"level\": 2,\n" +
            "          \"value\": 5\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"a2\",\n" +
            "      \"ownerID\": \"2\",\n" +
            "      \"units\": [\n" +
            "        {\n" +
            "          \"level\": 1,\n" +
            "          \"value\": 1\n" +
            "        },\n"+
            "        {\n" +
            "           \"level\": 2, \n" +
            "           \"value\": 3\n"+
            "        }\n"+
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    String src2 = "{\n" +
            "  \"map\": [\n" +
            "    {\n" +
            "      \"name\": \"g3\",\n" +
            "      \"ownerID\": \"1\",\n" +
            "      \"units\": [\n" +
            "        {\n" +
            "          \"level\": 1,\n" +
            "          \"value\": 3\n" +
            "        },\n" +
            "        {\n" +
            "          \"level\": 2,\n" +
            "          \"value\": 5\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"a3\",\n" +
            "      \"ownerID\": \"2\",\n" +
            "      \"units\": [\n" +
            "        {\n" +
            "          \"level\": 1,\n" +
            "          \"value\": 1\n" +
            "        },\n"+
            "        {\n" +
            "           \"level\": 2, \n" +
            "           \"value\": 3\n"+
            "        }\n"+
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    Map testMap;
    int playerID = 1;
    HashMap<String, Territory> playerTerritories;
    int player2ID = 2;
    HashMap<String, Territory> playerTerritories2;
    //public ServerJSONParser(String src, Map playerMap, int playerID, HashMap<String, Territory> playerTerritories)
    public void initialize(){
        testMap = new Map(3);
        playerTerritories = new HashMap<>();
        playerTerritories2 = new HashMap<>();
        //a1
        testMap.getTerritoryList().get("a1").getUnits().put(1, new ArrayList<>());
        testMap.getTerritoryList().get("a1").getUnits().put(2, new ArrayList<>());
        testMap.getTerritoryList().get("a1").setOwner(1);
        //add 3 units to level 1
        testMap.getTerritoryList().get("a1").getUnits().get(1).add(new Unit());
        testMap.getTerritoryList().get("a1").getUnits().get(1).add(new Unit());
        testMap.getTerritoryList().get("a1").getUnits().get(1).add(new Unit());
        //add 1 unit to level 2
        testMap.getTerritoryList().get("a1").getUnits().get(2).add(new Unit());
        //a2
        testMap.getTerritoryList().get("a2").getUnits().put(1, new ArrayList<>());
        testMap.getTerritoryList().get("a2").setOwner(1);
        //add 1 unit to level 1
        testMap.getTerritoryList().get("a2").getUnits().get(1).add(new Unit());
        //a3
        testMap.getTerritoryList().get("a3").getUnits().put(1, new ArrayList<>());
        //add 3 units to level 1
        testMap.getTerritoryList().get("a3").setOwner(1);
        testMap.getTerritoryList().get("a3").setOwner(1);
        testMap.getTerritoryList().get("a3").setOwner(1);

        playerTerritories.put("a1", testMap.getTerritoryList().get("a1"));
        playerTerritories.put("a2", testMap.getTerritoryList().get("a2"));


    }
    @Test
    void checkOwnership() {
        initialize();
        ServerJSONParser p1 = new ServerJSONParser(src, testMap, playerID, playerTerritories);
        ServerJSONParser p2 = new ServerJSONParser(src, testMap, player2ID, playerTerritories2);
        Territory t = playerTerritories.get("a1");
        Territory t2 = playerTerritories.get("a2");
        int new_owner = 2;
        assertEquals(t.getOwner(), 1);
        p1.checkOwnership(new_owner, t);
        assertEquals(t.getOwner(), 2);
        p1.checkOwnership(playerID, t2);
        assertEquals(t2.getOwner(), 1);
        assertEquals(t2.getOwner(), 1);
        p2.checkOwnership(new_owner, t2);
        assertEquals(t2.getOwner(), 2);
    }

    @Test
    void checkNumOfUnitsChanged() {
        initialize();
        ServerJSONParser p1 = new ServerJSONParser(src, testMap, playerID, playerTerritories);

        Territory t = playerTerritories.get("a1");
        Territory t2 = playerTerritories.get("a2");
        //checkNumOfUnitsChanged(JSONArray getUnitArr, Territory t);
        JSONArray getArray = p1.getJSONObj().getJSONArray("map");
        JSONObject getObject = getArray.getJSONObject(0);//a1
        System.out.println(getObject.toString());
        String terrName = getObject.getString("name");
        assertEquals(terrName, "a1");
        JSONArray getUnitArr = getObject.getJSONArray("units");
        System.out.println(getUnitArr.toString());
        //if number of units are not equal
        assertEquals(t.getUnits().get(2).size(), 1);
        p1.checkNumOfUnitsChanged(getUnitArr, t);
        assertEquals(t.getUnits().get(2).size(), 5);
        //if number of units are equal
        assertEquals(t.getUnits().get(1).size(), 3);
        //if the level does not exist

        JSONObject getObject2 = getArray.getJSONObject(1);//a2
        System.out.println(getObject2.toString());
        String terrName2 = getObject2.getString("name");
        assertEquals(terrName2, "a2");
        JSONArray getUnitArr2 = getObject2.getJSONArray("units");
        System.out.println(getUnitArr2.toString());
        assertEquals(t2.getUnits().get(2), null);
        p1.checkNumOfUnitsChanged(getUnitArr2, t2);
        assertEquals(t2.getUnits().get(2).size(), 3);
        //test edge cases
        //ServerJSONParser p3 = new ServerJSONParser(src2, testMap, playerID, playerTerritories);
        //Territory t3 = playerTerritories.get("a2");

    }

    @Test
    void doParse() {
        initialize();
        ServerJSONParser p1 = new ServerJSONParser(src, testMap, playerID, playerTerritories);
        Territory t = playerTerritories.get("a1");
        Territory t2 = playerTerritories.get("a2");
        assertEquals(t2.getOwner(), 1);
        p1.doParse();
        assertEquals(t.getUnits().get(2).size(), 5);
        assertEquals(t2.getUnits().get(2).size(), 3);
        assertEquals(t2.getOwner(), 2);

    }

    @Test
    void doException(){
        initialize();
        ServerJSONParser p1 = new ServerJSONParser(src2, testMap, playerID, playerTerritories);
        assertThrows(IllegalArgumentException.class, ()->p1.doParse());
    }

}