package edu.duke.ece651.client;

import edu.duke.ece651.shared.Territory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientJSONTest {

    @Test
    void convertTo() throws IOException {
        ArrayList<Action> actionList = new ArrayList<>();
        HashMap<Integer, Integer> unitMap = new HashMap<>();
        unitMap.put(1,2);
        unitMap.put(2,4);
        unitMap.put(3,1);

        actionList.add(new DeployAction(new Territory("Durham"),unitMap));
        actionList.add(new AttackAction(new Territory("Duke"), new Territory("Tex"),unitMap));
        ClientJSON converter = new ClientJSON(actionList);

        int spacesToIndentEachLevel = 2;

        String content = "{\"actions\": [\n" +
                "  {\n" +
                "    \"actionType\": \"deploy\",\n" +
                "    \"from\": null,\n" +
                "    \"to\": \"Durham\",\n" +
                "    \"units\": [\n" +
                "      {\n" +
                "        \"level\": 3,\n" +
                "        \"value\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"level\": 3,\n" +
                "        \"value\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"level\": 3,\n" +
                "        \"value\": 1\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"actionType\": \"attack\",\n" +
                "    \"from\": \"Duke\",\n" +
                "    \"to\": \"Tex\",\n" +
                "    \"units\": [\n" +
                "      {\n" +
                "        \"level\": 3,\n" +
                "        \"value\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"level\": 3,\n" +
                "        \"value\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"level\": 3,\n" +
                "        \"value\": 1\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]}";

        assertEquals(content, converter.convertTo().toString(spacesToIndentEachLevel));
    }
}