package edu.duke.ece651.shared;

import edu.duke.ece651.shared.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientJSONTest {

    @Test
    void convertTo() throws IOException {
        ArrayList<Action> actionList = new ArrayList<>();
        HashMap<Integer, Integer> unitHashMap = new HashMap<>();
        unitHashMap.put(1,2);
        unitHashMap.put(2,4);
        unitHashMap.put(3,1);

        actionList.add(new DeployAction(new Territory("Durham"),unitHashMap));
        actionList.add(new AttackAction(new Territory("Duke"), new Territory("Tex"),unitHashMap));
        ClientJSON converter = new ClientJSON(1, actionList);

        int spacesToIndentEachLevel = 2;

        String content = "{\n" +
                "  \"actions\": [\n" +
                "    {\n" +
                "      \"actionType\": \"deploy\",\n" +
                "      \"from\": null,\n" +
                "      \"to\": \"Durham\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 2\n" +
                "        },\n" +
                "        {\n" +
                "          \"level\": 2,\n" +
                "          \"value\": 4\n" +
                "        },\n" +
                "        {\n" +
                "          \"level\": 3,\n" +
                "          \"value\": 1\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"actionType\": \"attack\",\n" +
                "      \"from\": \"Duke\",\n" +
                "      \"to\": \"Tex\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 2\n" +
                "        },\n" +
                "        {\n" +
                "          \"level\": 2,\n" +
                "          \"value\": 4\n" +
                "        },\n" +
                "        {\n" +
                "          \"level\": 3,\n" +
                "          \"value\": 1\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"playerID\": 1\n" +
                "}";

        assertEquals(content, converter.convertTo().toString(spacesToIndentEachLevel));
    }
}