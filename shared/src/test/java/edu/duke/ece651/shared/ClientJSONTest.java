package edu.duke.ece651.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.ClientJSON;
import edu.duke.ece651.shared.Unit;
import edu.duke.ece651.shared.Upgrade;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientJSONTest {

    @Test
    public void test_ClientJSON() throws IOException {
        String clientJSON_str = Files.readString(Path.of("../JSON_Sample/clientJSON.json"), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        ClientJSON clientJSON = objectMapper.readValue(clientJSON_str,ClientJSON.class);
        // Test playerID
        assertEquals(clientJSON.getPlayerID(), 123);
        clientJSON.setPlayerID(111);
        assertEquals(clientJSON.getPlayerID(), 111);

        // Test playAgain
        assertEquals(clientJSON.getPlayAgain(),false);
        clientJSON.setPlayAgain(true);
        assertEquals(clientJSON.getPlayAgain(), true);

        // Test playerNum
        assertEquals(clientJSON.getPlayerNum(),3);
        clientJSON.setPlayerNum(2);
        assertEquals(clientJSON.getPlayerNum(), 2);

        // Test techUpgrade
        assertEquals(clientJSON.getTechUpgrade(),true);
        clientJSON.setTechUpgrade(false);
        assertEquals(clientJSON.getTechUpgrade(), false);

        // Test upgradeList and upgrade
        ArrayList<Upgrade> upgradeArrayList_test= new ArrayList<>();

            // Test upgrade set and get
            upgradeArrayList_test.add(new Upgrade().setTerritory("A").setLevelFrom(0).setLevelTo(3).setNumOfUnits(2));
            upgradeArrayList_test.add(new Upgrade().setTerritory("A").setLevelFrom(2).setLevelTo(4).setNumOfUnits(1));
            upgradeArrayList_test.add(new Upgrade().setTerritory("B").setLevelFrom(0).setLevelTo(3).setNumOfUnits(2));

            assertEquals(upgradeArrayList_test.get(0).getTerritory(), "A");
            assertEquals(upgradeArrayList_test.get(0).getLevelFrom(), 0);
            assertEquals(upgradeArrayList_test.get(0).getLevelTo(), 3);
            assertEquals(upgradeArrayList_test.get(0).getNumOfUnits(), 2);


        // Test upgrade equal
        assertEquals(clientJSON.getUpgradeList().get(0),upgradeArrayList_test.get(0));
        assertEquals(clientJSON.getUpgradeList().get(0),clientJSON.getUpgradeList().get(0));

        // Test ActionList and action and unit
        ArrayList<Action> actionArrayList = new ArrayList<>();
        ArrayList<Unit> unitArrayList = new ArrayList<>();


            // Test Unit Set and get
            unitArrayList.add(new Unit().setLevel(0).setValue(9));
            unitArrayList.add(new Unit().setLevel(1).setValue(0));
            unitArrayList.add(new Unit().setLevel(2).setValue(0));
            unitArrayList.add(new Unit().setLevel(3).setValue(0));
            unitArrayList.add(new Unit().setLevel(4).setValue(0));
            unitArrayList.add(new Unit().setLevel(5).setValue(0));
            unitArrayList.add(new Unit().setLevel(6).setValue(0));

            assertEquals(unitArrayList.get(0), new Unit().setLevel(0).setValue(9));
            assertEquals(unitArrayList.get(0).getLevel(), 0);
            assertEquals(unitArrayList.get(0).getValue(), 9);
            assertEquals(unitArrayList.get(0), unitArrayList.get(0));

        // Test Action set and get
        actionArrayList.add(new Action().setActionType("attack").setFrom("A").setTo("B").setUnits(unitArrayList));
        actionArrayList.add(new Action().setActionType("move").setFrom("A").setTo("B").setUnits(unitArrayList));
        actionArrayList.add(new Action().setActionType("move").setFrom("A").setTo("B").setUnits(unitArrayList));
        actionArrayList.add(new Action().setActionType("deploy").setFrom(null).setTo("A").setUnits(unitArrayList));

        assertEquals(actionArrayList.get(0).getActionType(), "attack");
        assertEquals(actionArrayList.get(0).getFrom(), "A");
        assertEquals(actionArrayList.get(0).getTo(), "B");
        assertEquals(actionArrayList.get(0).getUnits(), unitArrayList);
        assertEquals(clientJSON.getActionList(), actionArrayList);
        assertEquals(clientJSON.getActionList().get(0), clientJSON.getActionList().get(0));


        // Test clientJSON equal
        ClientJSON clientJSON_expected = new ClientJSON().setPlayerID(111).setPlayerNum(2)
                .setPlayAgain(true).setTechUpgrade(false).setUpgradeList(upgradeArrayList_test).setActionList(actionArrayList);
        assertEquals(clientJSON, clientJSON_expected);

        // Test clientJSON not equal
        ClientJSON clientJSON_expected_not_equal = new ClientJSON().setPlayerID(112).setPlayerNum(2)
                .setPlayAgain(true).setTechUpgrade(false).setUpgradeList(upgradeArrayList_test).setActionList(actionArrayList);
        assertNotEquals(clientJSON, clientJSON_expected_not_equal);

        // Test self equal
        assertEquals(clientJSON, clientJSON);

    }

}