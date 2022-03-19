package edu.duke.ece651.client;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.PlayerCounter;
import edu.duke.ece651.shared.Territory;
import edu.duke.ece651.shared.Unit;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    ByteArrayOutputStream bytes;
    @Test
    void constructorTest() {
        bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(""));
        PrintStream output = new PrintStream(bytes, true);
        Player p1 = new Player(1,3, input, output);
        assertEquals(p1.getId(), 1);
        assertEquals(p1.getWholeMap().getTerritoryList().size(), 9);
        assertEquals(p1.getMyTerritories().size(), 3);
    }

    @Test
    void multiPlayersTest(){
        bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(""));
        PrintStream output = new PrintStream(bytes, true);
        PlayerCounter player_counter = null;//count player id
        Player p1 = new Player(player_counter.getInstance().getCurrent_id(),3, input, output);//id: 1
        Player p2 = new Player(player_counter.getInstance().getCurrent_id(),3, input, output);//id: 2
        Player p3 = new Player(player_counter.getInstance().getCurrent_id(),3, input, output);//id: 3
        //check ids
        assertEquals(p1.getId(), 1);
        assertEquals(p2.getId(), 2);
        assertEquals(p3.getId(), 3);
        //check initial territories
        for (String i : p1.getMyTerritories().keySet()) {
            System.out.println("key: " + i + " value: " + p1.getMyTerritories().get(i));
            assertEquals(p1.getWholeMap().getTerritoryList().get(i), p1.getMyTerritories().get(i));
        }

        for (String i : p2.getMyTerritories().keySet()) {
            System.out.println("key: " + i + " value: " + p2.getMyTerritories().get(i));
            assertEquals(p2.getWholeMap().getTerritoryList().get(i), p2.getMyTerritories().get(i));
        }

        for (String i : p3.getMyTerritories().keySet()) {
            System.out.println("key: " + i + " value: " + p3.getMyTerritories().get(i));
            assertEquals(p3.getWholeMap().getTerritoryList().get(i), p3.getMyTerritories().get(i));
        }
    }


    private Player createTextPlayer(String inputData){
        bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Player testPlayer = new Player(1, 3, input, output);

        //set player ID to other territories
        for(int id = 2; id<=3; id++){
            ArrayList<String> group = testPlayer.getWholeMap().getGroups().get(id-1);
            for(String str: group){
                testPlayer.getWholeMap().getTerritoryList().get(str).setOwner(id);
            }
        }
        String src = "{\n" +
                "  \"map\": [\n" +
                "    {\n" +
                "      \"name\": \"a1\",\n" +
                "      \"ownerID\": \"1\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 3\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"a2\",\n" +
                "      \"ownerID\": \"1\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 3\n" +
                "        }\n"+
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"a3\",\n" +
                "      \"ownerID\": \"1\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 3\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"b1\",\n" +
                "      \"ownerID\": \"2\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 3\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"b2\",\n" +
                "      \"ownerID\": \"2\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 3\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"b3\",\n" +
                "      \"ownerID\": \"2\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 3\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"c1\",\n" +
                "      \"ownerID\": \"3\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 3\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"c2\",\n" +
                "      \"ownerID\": \"3\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 3\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"c3\",\n" +
                "      \"ownerID\": \"3\",\n" +
                "      \"units\": [\n" +
                "        {\n" +
                "          \"level\": 1,\n" +
                "          \"value\": 3\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        testPlayer.setRecvJSON(src);
        return testPlayer;
    }


    @Test
    void playerDoDeployTest() throws IOException, Exception{
        int a[]=new int[3];
        a[0] = 3;
        a[1] = 4;
        a[2] = 2;
        String inputs[] = new String[3];
        inputs[0] = "3\n3\n3\n";
        inputs[1] = "3\n1\n1\n4\n";
        inputs[2] = "3\n6\n";

        for(int round = 0; round < 3; round++) {
            System.out.println("--------------------------------------------------------------------");
            Player testPlayer = createTextPlayer(inputs[round]);
            assertEquals(testPlayer.getMyTerritories().size(), 3);
            for (Integer i : testPlayer.getTotalDeployment().keySet()) {
                System.out.println("Level " + i + ", " + testPlayer.getTotalDeployment().get(i));
            }
            testPlayer.playerDoDeploy(testPlayer.getInputReader(), testPlayer.getOut());
            System.out.println(bytes.toString());

            assertEquals(testPlayer.getActionList().size(), a[round]);
            for(int j = 0; j < a[round]; j++){
                System.out.println(testPlayer.getActionList().get(j).getTo().getName() +", " +
                        testPlayer.getActionList().get(j).getUnitNumber());
            }
        }

        //test corner cases
        System.out.println("--------------------------------------------------------------------");
        Player testPlayer = createTextPlayer("3\n4\n\nw\n6\n2\n");
        assertEquals(testPlayer.getMyTerritories().size(), 3);
        for (Integer i : testPlayer.getTotalDeployment().keySet()) {
            System.out.println("Level " + i + ", " + testPlayer.getTotalDeployment().get(i));
        }
        testPlayer.playerDoDeploy(testPlayer.getInputReader(), testPlayer.getOut());
        System.out.println(bytes.toString());

        assertEquals(testPlayer.getActionList().size(), 3);
        for(int j = 0; j < 3; j++){
            System.out.println(testPlayer.getActionList().get(j).getTo().getName() +", " +
                    testPlayer.getActionList().get(j).getUnitNumber());
        }

        System.out.println("--------------------------------------------------------------------");
        Player testPlayer2 = createTextPlayer("3\n-1\n3\n3\n");
        assertEquals(testPlayer.getMyTerritories().size(), 3);
        for (Integer i : testPlayer.getTotalDeployment().keySet()) {
            System.out.println("Level " + i + ", " + testPlayer.getTotalDeployment().get(i));
        }
        testPlayer.playerDoDeploy(testPlayer.getInputReader(), testPlayer.getOut());
        System.out.println(bytes.toString());

        assertEquals(testPlayer.getActionList().size(), 3);
        for(int j = 0; j < 3; j++){
            System.out.println(testPlayer.getActionList().get(j).getTo().getName() +", " +
                    testPlayer.getActionList().get(j).getUnitNumber());
        }
    }

    @Test
    void testDeploy() throws Exception{

        Player testPlayer = createTextPlayer("3\n1\n1\n4\n");
        assertEquals(testPlayer.getTotalDeployment().get(1), 9);
        assertEquals(testPlayer.deploy(9, "a1"),true);
        assertEquals(testPlayer.getTotalDeployment().get(1), 0);
        assertEquals(testPlayer.deploy(3, "a2"), false);

        Player testPlayer2 = createTextPlayer("3\n1\n1\n4\n");
        assertEquals(testPlayer2.getTotalDeployment().get(1), 9);
        assertEquals(testPlayer2.deploy(3, "a1"),true);
        assertEquals(testPlayer2.getTotalDeployment().get(1), 6);
        //assertThrows(NullPointerException.class, () -> testPlayer2.deploy(3, "j2"));
    }

    @Test
    void testPlayerDoMove() throws IOException{
        //wrong from_name/ correct from_name/ wrong level/ correct level/ wrong units#(-1)/
        //wrong units#(0)/ wrong units#(4)/wrong units#(\ng)/ correct units#(2)/wrong to_name(b3)
        //correct to_name(a1)
        Player testPlayer = createTextPlayer("b3\na3\n99\n1\n-1\n0\n4\n\ng\n2\nb3\na1\n");
        //deploy 3 level-1 units to a1
        testPlayer.getMyTerritories().get("a1").getUnits().put(1, new ArrayList<>());
        testPlayer.getMyTerritories().get("a1").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a1").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a1").getUnits().get(1).add(new Unit(1));
        //deploy 3 level-1 units to a2
        testPlayer.getMyTerritories().get("a2").getUnits().put(1, new ArrayList<>());
        testPlayer.getMyTerritories().get("a2").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a2").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a2").getUnits().get(1).add(new Unit(1));
        //deploy 3 level-1 units to a3
        testPlayer.getMyTerritories().get("a3").getUnits().put(1, new ArrayList<>());
        testPlayer.getMyTerritories().get("a3").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a3").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a3").getUnits().get(1).add(new Unit(1));

        testPlayer.playerDoMove(testPlayer.getInputReader(), testPlayer.getOut());
        System.out.println("-----------------start--------------------");
        System.out.println(bytes.toString());
        System.out.println("-----------------end--------------------");
        bytes.reset();
        System.out.println("From " + testPlayer.getActionList().get(0).getFrom().getName() + " to " + testPlayer.getActionList().get(0).getTo().getName() +", move" +
                testPlayer.getActionList().get(0).getUnitNumber());
    }
    @Test
    void testMove(){
        Player testPlayer = createTextPlayer("3\n1\n1\n4\n");
        HashMap<Integer, Integer> attackUnits = new HashMap<>();
        attackUnits.put(1, 2);
        String from_name = "ab3";
        String to_name = "b1";
        assertFalse(testPlayer.move(attackUnits, from_name, to_name));
    }


    @Test
    void testPlayerDoAttack() throws IOException{
        //wrong from_name; correct from_name; wrong level(99); wrong level(\n); wrong level(k); correct level(1); wrong unit#(-1);
        // wrong unit#(0); wrong unit#(4); wrong unit#(\n\nj); correct unit#; 2 wrong to names(j3, a1); correct to_name(b1);
        Player testPlayer = createTextPlayer("b3\na1\n99\n1\n-1\n\nk\n0\n4\n\nj\n2\nj3\na1\nb1\n");
        //deploy 3 level-1 units to a1
        testPlayer.getMyTerritories().get("a1").getUnits().put(1, new ArrayList<>());
        testPlayer.getMyTerritories().get("a1").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a1").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a1").getUnits().get(1).add(new Unit(1));
        //deploy 3 level-1 units to a2
        testPlayer.getMyTerritories().get("a2").getUnits().put(1, new ArrayList<>());
        testPlayer.getMyTerritories().get("a2").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a2").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a2").getUnits().get(1).add(new Unit(1));
        //deploy 3 level-1 units to a3
        testPlayer.getMyTerritories().get("a3").getUnits().put(1, new ArrayList<>());
        testPlayer.getMyTerritories().get("a3").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a3").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a3").getUnits().get(1).add(new Unit(1));

        testPlayer.playerDoAttack(testPlayer.getInputReader(), testPlayer.getOut());
        System.out.println("-----------------start--------------------");
        System.out.println(bytes.toString());
        System.out.println("-----------------end--------------------");
        bytes.reset();
        System.out.println("From " + testPlayer.getActionList().get(0).getFrom().getName() + " to " + testPlayer.getActionList().get(0).getTo().getName() +", attack" +
                testPlayer.getActionList().get(0).getUnitNumber());
    }

    @Test
    void testAttack() {

        Player testPlayer = createTextPlayer("3\n1\n1\n4\n");
        HashMap<Integer, Integer> attackUnits = new HashMap<>();
        attackUnits.put(1, 2);
        String from_name = "ab3";
        String to_name = "b1";
        assertFalse(testPlayer.attack(attackUnits, from_name, to_name));
        //assertThrows(Exception.class, ()->testPlayer.attack(attackUnits, from_name, to_name));
    }

    @Test
    void testPlayerMakeChoice() throws IOException {
        String select_cmd1 = "c\nM\n";
        String move_cmd = "a3\n1\n1\na1\n";
        String select_cmd2 = "A\n";
        String attack_cmd = "a1\n1\n2\nb1\n";
        String select_cmd3 = "d\n";
        Player testPlayer = createTextPlayer(select_cmd1+move_cmd+select_cmd2+
                attack_cmd + select_cmd3);

        //deploy 3 level-1 units to a1
        testPlayer.getMyTerritories().get("a1").getUnits().put(1, new ArrayList<>());
        testPlayer.getMyTerritories().get("a1").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a1").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a1").getUnits().get(1).add(new Unit(1));
        //deploy 3 level-1 units to a2
        testPlayer.getMyTerritories().get("a2").getUnits().put(1, new ArrayList<>());
        testPlayer.getMyTerritories().get("a2").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a2").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a2").getUnits().get(1).add(new Unit(1));
        //deploy 3 level-1 units to a3
        testPlayer.getMyTerritories().get("a3").getUnits().put(1, new ArrayList<>());
        testPlayer.getMyTerritories().get("a3").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a3").getUnits().get(1).add(new Unit(1));
        testPlayer.getMyTerritories().get("a3").getUnits().get(1).add(new Unit(1));

        testPlayer.playerMakeChoice(testPlayer.getInputReader(), testPlayer.getOut());

        System.out.println("-----------------start--------------------");
        System.out.println(bytes.toString());
        System.out.println("-----------------end--------------------");
        bytes.reset();
        assertEquals(testPlayer.getActionList().size(), 2);
        for(int i = 0; i < testPlayer.getActionList().size(); i++) {
            Action a = testPlayer.getActionList().get(i);
            System.out.println("From " + a.getFrom().getName() + " to " + a.getTo().getName() + ", units " +
                    a.getUnitNumber());
        }
    }

    public Player createBetterPlayer(){
        String select_cmd1 = "c\nM\n";
        String move_cmd = "a3\n1\n1\na1\n";
        String select_cmd2 = "A\n";
        String attack_cmd = "a1\n1\n2\nb1\n";
        String select_cmd3 = "d\n";
        Player testPlayer = createTextPlayer(select_cmd1+move_cmd+select_cmd2+
                attack_cmd + select_cmd3);
        return testPlayer;
    }

    public Player createAttackCheckerPlayer(){
        String select_cmd1 = "c\nM\n";
        String move_cmd2 = "a1\n1\n1\na3\n";
        String select_cmd2 = "A\n";
        String attack_cmd = "a3\n1\n2\nb1\n";//not adjacent
        String attack_cmd2 = "a1\n1\n1\nb1\n";
        String select_cmd3 = "d\n";
        Player testPlayer = createTextPlayer(select_cmd1 + move_cmd2 +select_cmd2+
                attack_cmd + attack_cmd2 + select_cmd3);
        return testPlayer;
    }

    public Player createEmptyPlayer(){

        Player testPlayer = createTextPlayer("");
        return testPlayer;
    }

    @Test
    void testPlayOneRound() throws Exception {
        //case 1: lose, but not the first round
        System.out.println("/////////////////////////////////////case 1: lose, but not the first round////////////////////////////////////");
        Player testPlayer = createEmptyPlayer();
        testPlayer.setIsFirstRound(false);
        testPlayer.setIsLose(true);
        testPlayer.playOneRound();
        System.out.println(bytes.toString());
        assertEquals(testPlayer.getActionList().size(), 0);
        //case 2: gg and win
        System.out.println("/////////////////////////////////////case 2: gg and win////////////////////////////////////");
        Player testPlayer2 = createEmptyPlayer();
        testPlayer2.setIsFirstRound(false);
        testPlayer2.setIsWon(true);
        testPlayer2.setIsGameOver(true);
        testPlayer2.playOneRound();
        System.out.println(bytes.toString());
        //case 3: gg but lose
        System.out.println("/////////////////////////////////////case 3: gg but lose////////////////////////////////////");
        Player testPlayer3 = createEmptyPlayer();
        testPlayer3.setIsFirstRound(false);
        testPlayer3.setIsLose(true);
        testPlayer3.setIsGameOver(true);
        testPlayer3.playOneRound();
        System.out.println(bytes.toString());
        //case 4: is first round
        System.out.println("/////////////////////////////////////case 4: is first round////////////////////////////////////");
        Player testPlayer4 = createTextPlayer("3\n1\n1\n4\n");
        testPlayer4.playOneRound();
        assertEquals(testPlayer4.getActionList().size(), 4);
        for(int i = 0 ; i < 4; i++) {
            System.out.println(testPlayer4.getActionList().get(i).getTo().getName() + ", " +
                    testPlayer4.getActionList().get(i).getUnitNumber());
        }
        //case 5: not first round
        System.out.println("/////////////////////////////////////case 5: not first round////////////////////////////////////");
        Player testPlayer5 = createBetterPlayer();
        testPlayer5.setIsFirstRound(false);
        testPlayer5.playOneRound();
        System.out.println(bytes.toString());
        assertEquals(testPlayer5.getActionList().size(), 2);
        for(int i = 0 ; i < 2; i++) {
            Action a = testPlayer5.getActionList().get(i);
            System.out.println("From " + a.getFrom().getName() + " to " + a.getTo().getName() + ", units " +
                    a.getUnitNumber());
        }
    }

    @Test
    void testAttackAndMoveChecker()throws Exception {
        //case 6: attack checker
        System.out.println("/////////////////////////////////////case 6: attack checker////////////////////////////////////");
        Player testPlayer = createAttackCheckerPlayer();
        testPlayer.setIsFirstRound(false);
        testPlayer.playOneRound();
        System.out.println(bytes.toString());
        assertEquals(testPlayer.getActionList().size(), 2);
        for(int i = 0 ; i < testPlayer.getActionList().size(); i++) {
            Action a = testPlayer.getActionList().get(i);
            System.out.println("From " + a.getFrom().getName() + " to " + a.getTo().getName() + ", units " +
                    a.getUnitNumber());
        }
    }

    @Test
    void testGetter(){
        /**
         public boolean getIsLose(){return isLose;}
         public boolean getIsGameOver(){return isGameOver;}
         public boolean getIsWon(){return isWon;}
         public boolean getIsFirstRound(){return isFirstRound;}
         **/
        Player testPlayer = createEmptyPlayer();
        boolean isLose = testPlayer.getIsLose();
        assertEquals(false, isLose);
        boolean isGG = testPlayer.getIsGameOver();
        assertEquals(false, isGG);
        boolean isWon = testPlayer.getIsWon();
        assertEquals(false, isWon);
        boolean isFirst = testPlayer.getIsFirstRound();
        assertEquals(true, isFirst);
        int numPlayers = testPlayer.getNumOfPlayers();
        assertEquals(3, numPlayers);
    }

    @Test
    void territoryTest(){
        Player testPlayer = createAttackCheckerPlayer();
        Unit u = new Unit(1);
        testPlayer.getWholeMap().getTerritoryList().get("a1").addUnit(u);
        assertEquals(testPlayer.getWholeMap().getTerritoryList().get("a1").getUnits().get(1).size(),
                testPlayer.getMyTerritories().get("a1").getUnits().get(1).size());
    }
}
