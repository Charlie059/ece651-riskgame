package edu.duke.ece651.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.shared.JSONMap;
import edu.duke.ece651.shared.Territory;
import edu.duke.ece651.shared.Unit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JSONMapTest {

    @Test
    public void test_JSONMap() throws IOException {
        String JSONMap_str = Files.readString(Path.of("../JSON_Sample/serverJSON.json"), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        JSONMap jsonMap = objectMapper.readValue(JSONMap_str,JSONMap.class);

        // Test isDeploy
        assertEquals(jsonMap.getIsDeploy(), true);

        // Test numOfPlayer
        assertEquals(jsonMap.getNumOfPlayer(),4);

        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Unit().setLevel(0).setValue(9));
        units.add(new Unit().setLevel(1).setValue(0));
        units.add(new Unit().setLevel(2).setValue(0));
        units.add(new Unit().setLevel(3).setValue(0));
        units.add(new Unit().setLevel(4).setValue(0));
        units.add(new Unit().setLevel(5).setValue(0));
        units.add(new Unit().setLevel(6).setValue(0));

        ArrayList<String> neighbor = new ArrayList<>();
        neighbor.add("a2");
        neighbor.add("b1");



        // Test territory
        ArrayList terrArr = new ArrayList();
        terrArr.add(new Territory().setSize(2).setName("a1").setOwnerID(1).setUnits(units).setNeighbour(neighbor));
        terrArr.add(new Territory().setSize(2).setName("a2").setOwnerID(1).setUnits(units).setNeighbour(neighbor));
        terrArr.add(new Territory().setSize(2).setName("b1").setOwnerID(2).setUnits(units).setNeighbour(neighbor));


        JSONMap jsonMapExpect = new JSONMap().setIsDeploy(true).setNumOfPlayer(4).setTerritory(terrArr);
        assertEquals(jsonMap, jsonMapExpect);
        assertEquals(jsonMap, jsonMap);
        assertEquals(jsonMap.getTerritory(), jsonMapExpect.getTerritory());
        assertEquals(jsonMap.getTerritory().get(0).getSize(), jsonMapExpect.getTerritory().get(0).getSize());
        assertEquals(jsonMap.getTerritory().get(0).getName(), jsonMapExpect.getTerritory().get(0).getName());
        assertEquals(jsonMap.getTerritory().get(0).getOwnerID(), jsonMapExpect.getTerritory().get(0).getOwnerID());
        assertEquals(jsonMap.getTerritory().get(0).getUnits(), jsonMapExpect.getTerritory().get(0).getUnits());
        assertEquals(jsonMap.getTerritory().get(0).getNeighbour(), jsonMapExpect.getTerritory().get(0).getNeighbour());
        assertEquals(jsonMap.getTerritory().get(0), jsonMap.getTerritory().get(0));



    }

}