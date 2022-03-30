package edu.duke.ece651.server;

import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.Visitor.ActionVisitor;

import java.net.Socket;
import java.util.HashMap;

/**
 * Check Action correctness
 * FeedBack Success or Failed
 */
public class ActionCheckDoFeedbackVistor implements ActionVisitor {

    private Socket clientSocket;
    private HashMap<Integer, Game> gameHashMap;
    private HashMap<Integer, Player> playerHashMap;
    public ActionCheckDoFeedbackVistor(Socket clientSocket, HashMap<Integer, Player> playerHashMap, HashMap<Integer, Game> gameHashMap) {
        this.clientSocket = clientSocket;
        this.playerHashMap = playerHashMap;
        this.gameHashMap = gameHashMap;
    }

    @Override
    public void visit(AttackAction attackAction) {
        //Check

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
