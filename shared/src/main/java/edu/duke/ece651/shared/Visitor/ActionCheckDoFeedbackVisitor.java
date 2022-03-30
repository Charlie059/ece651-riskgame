package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.Checker.AuthentificationChecker;
import edu.duke.ece651.shared.Wrapper.CurrGameID;
import edu.duke.ece651.shared.Wrapper.PlayerID;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.IO.ServerResponse.RSPSignupFail;
import edu.duke.ece651.shared.IO.ServerResponse.RSPSignupSuccess;
import edu.duke.ece651.shared.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * Check Action correctness
 * FeedBack Success or Failed
 */
public class ActionCheckDoFeedbackVisitor implements ActionVisitor {

    private PlayerID playerID;
    private CurrGameID currGameID;
    private Socket clientSocket;
    private HashMap<Integer, Game> gameHashMap;
    private HashMap<String, Player> playerHashMap;
    private ObjectStream objectStream;

    /**
     * Construct Checker, all by Communicator Reference
     * @param playerID PlayerID Object reference
     * @param currGameID CurrGameID Object reference
     * @param clientSocket ClientSocket Object referece
     * @param playerHashMap PlayerHashMap Object reference
     * @param gameHashMap GameHashMap Object reference
     */
    public ActionCheckDoFeedbackVisitor(PlayerID playerID, CurrGameID currGameID, Socket clientSocket, ObjectStream objectStream, HashMap<String, Player> playerHashMap, HashMap<Integer, Game> gameHashMap) {
        //Comunicator Reference
        this.playerID = playerID;
        this.currGameID = currGameID;
        this.clientSocket = clientSocket;
        this.objectStream = objectStream;
        this.playerHashMap = playerHashMap;
        this.gameHashMap = gameHashMap;
    }

    @Override
    public void visit(AttackAction attackAction) {
        //Check
        //GameAvaliableChecker gameAvaliableChecker = new GameAvaliableChecker(this.gameHashMap,this.playerHashMap);
        //Do

        //Feedback
        Game currGame = this.gameHashMap.get(attackAction.getGameID());


    }

    @Override
    public void visit(CommitAction commitAction) {

    }

    @Override
    public void visit(DeployAction deployAction) {


    }

    @Override
    public void visit(JoinAction joinAction) {

    }

    @Override
    public void visit(LoginAction loginAction) {

    }

    @Override
    public void visit(LogoutAction logoutAction) {

    }

    @Override
    public void visit(MoveAction moveAction) {

    }

    @Override
    public void visit(NewGameAction newGameAction) {

    }

    @Override
    public void visit(PlayAgainAction playAgainAction) {

    }

    @Override
    public void visit(QuitGameAction quitGameAction) {

    }

    @Override
    public void visit(SignUpAction signUpAction) {
        // Check Authentication
        AuthentificationChecker authentificationChecker = new AuthentificationChecker(this.gameHashMap, this.playerHashMap, signUpAction.getAccount(),signUpAction.getPassword());
        // If account not exist
        if(authentificationChecker.doCheck()){
            //DO Create New Account
            Player player = new Player();
            player.setPassword(signUpAction.getPassword());
            this.playerHashMap.put(signUpAction.getAccount(),player);
            //Successful Response
            RSPSignupSuccess rspSignupSuccess = new RSPSignupSuccess();
            try {
                this.objectStream.sendObject(rspSignupSuccess);
            } catch (IOException e) {
                System.out.println("Socket Disconnected, Send Failed!");
            }
        }
        else {
            //Failed Response
            RSPSignupFail rspSignupFail = new RSPSignupFail();
            try {
                this.objectStream.sendObject(rspSignupFail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }


    @Override
    public void visit(SwitchGameAction switchGameAction) {

    }

    @Override
    public void visit(UpdateTechAction updateTechAction) {

    }

    @Override
    public void visit(UpdateUnitsAction updateUnitsAction) {

    }
}
