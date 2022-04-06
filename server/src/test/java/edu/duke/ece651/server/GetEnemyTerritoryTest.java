package edu.duke.ece651.server;

import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetEnemyTerritoryTest {
    @Test
    public void test_getEnemyTerritoryTest(){
        GameID currGameID = new GameID(1);

        AccountID hostAccountID = new AccountID("abcde");
        AccountID joinerAccountID = new AccountID("cdefg");
        //Host
        Game currGame = new Game(2);
        GameHashMap gameHashMap = new GameHashMap();
        gameHashMap.put(currGameID,currGame);
        Player host = new Player(hostAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(hostAccountID, host);
        currGame.setOwnership(hostAccountID);
        host.assignMyTerritories();
        currGame.getCommittedHashMap().put(hostAccountID, false);

        Player joiner = new Player(joinerAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(joinerAccountID, joiner);
        currGame.setOwnership(joinerAccountID);
        joiner.assignMyTerritories();

        assertTrue(currGame.getPlayerHashMap().getEnemyTerritories(hostAccountID).containsKey(joinerAccountID.getAccountID()));
        assertTrue(currGame.getPlayerHashMap().getEnemyTerritories(hostAccountID).get(joinerAccountID.getAccountID()).size()==3);
//        for(int i=0;i<currGame.getPlayerHashMap().getEnemyTerritories(hostAccountID).get(joinerAccountID.getAccountID()).size();i++){
//            System.out.println(currGame.getPlayerHashMap().getEnemyTerritories(hostAccountID).get(joinerAccountID.getAccountID()).get(i));
//        }
    }
}
