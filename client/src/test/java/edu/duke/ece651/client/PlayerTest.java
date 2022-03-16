package edu.duke.ece651.client;

import edu.duke.ece651.shared.PlayerCounter;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    ByteArrayOutputStream bytes;
    @Test
    void constructorTest() {
        Player p1 = new Player(1,3);
        assertEquals(p1.getId(), 1);
        assertEquals(p1.getWholeMap().getTerritoryList().size(), 9);
        assertEquals(p1.getMyTerritories().size(), 3);
    }

    @Test
    void multiPlayersTest(){
        PlayerCounter player_counter = null;//count player id
        Player p1 = new Player(player_counter.getInstance().getCurrent_id(),3);//id: 1
        Player p2 = new Player(player_counter.getInstance().getCurrent_id(),3);//id: 2
        Player p3 = new Player(player_counter.getInstance().getCurrent_id(),3);//id: 3
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

    @Test
    void playOneRoundTEST() throws IOException {

    }

    private Player createTextPlayer(String inputData){
        bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Player testPlayer = new Player(1, 3, input, output);
        return testPlayer;
        //Player(int _id, int _numOfPlayer, BufferedReader _in, PrintStream _out)
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
        Player testPlayer = createTextPlayer("3\n4\n6\n2\n");
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
        assertThrows(NullPointerException.class, () -> testPlayer2.deploy(3, "j2"));

    }

    @Test
    void testPlayerDoMove(){
        Player testPlayer = createTextPlayer("");
    }
    //playerDoMove(BufferedReader inputReader, PrintStream out) throws IOException
}