package edu.duke.ece651.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;

class ServerJSONTest {

    @Test
    void convertTo() {
        Map testMap = new Map(3);
        HashMap<String, Territory> playerTerritories = new HashMap<>();
        HashMap<String, Territory> playerTerritories2 = new HashMap<>();
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

        playerTerritories.put("a1", testMap.getTerritoryList().get("a1"));
        playerTerritories.put("a2", testMap.getTerritoryList().get("a2"));


        ServerJSON serverJSON = new ServerJSON(testMap);

        String except = "{\"map\": [\n" +
                "    {\n" +
                "        \"name\": \"a1\",\n" +
                "        \"units\": [\n" +
                "            {\n" +
                "                \"level\": 1,\n" +
                "                \"value\": 3\n" +
                "            },\n" +
                "            {\n" +
                "                \"level\": 2,\n" +
                "                \"value\": 1\n" +
                "            }\n" +
                "        ],\n" +
                "        \"ownerID\": 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"b2\",\n" +
                "        \"units\": [],\n" +
                "        \"ownerID\": -1\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"c3\",\n" +
                "        \"units\": [],\n" +
                "        \"ownerID\": -1\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"a2\",\n" +
                "        \"units\": [{\n" +
                "            \"level\": 1,\n" +
                "            \"value\": 1\n" +
                "        }],\n" +
                "        \"ownerID\": 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"b3\",\n" +
                "        \"units\": [],\n" +
                "        \"ownerID\": -1\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"a3\",\n" +
                "        \"units\": [],\n" +
                "        \"ownerID\": -1\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"c1\",\n" +
                "        \"units\": [],\n" +
                "        \"ownerID\": -1\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"b1\",\n" +
                "        \"units\": [],\n" +
                "        \"ownerID\": -1\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"c2\",\n" +
                "        \"units\": [],\n" +
                "        \"ownerID\": -1\n" +
                "    }\n" +
                "]}";
        Assertions.assertEquals(except, serverJSON.convertTo().toString(4));
    }
}