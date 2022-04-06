package edu.duke.ece651.client.IO;

import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Unit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class MockServer {
    private final int portNum;
    private final ServerSocket serversocket;
    private Socket clientSocket;

    public MockServer(int portNum) throws IOException {
        this.portNum = portNum;
        this.serversocket = new ServerSocket(this.portNum);
    }

    /**
     * Accept the connection from the client
     */
    public Socket acceptClient() throws IOException {
       this.clientSocket = this.serversocket.accept();
       return this.clientSocket;
    }

    public void closeSocket() throws IOException {
        this.serversocket.close();
    }


    /**
     * Send object to server
     * @return true if success
     */
    public boolean sendObject(Object object) throws IOException {
        ObjectStream objectStream = new ObjectStream(this.clientSocket);
        return objectStream.sendObject(object);
    }


    /**
     * Recv object from server
     * @return true if success
     */
    public Object recvObject() throws IOException, ClassNotFoundException {
        ObjectStream objectStream = new ObjectStream(this.clientSocket);
        return objectStream.recvObject();
    }



    // Choose an action and return
    public Response chooseAction(String input){
        switch (input){
            // RSPChooseJoinGameFail
            case "1":{
                return new RSPChooseJoinGameFail();
            }
            // RSPChooseJoinGameSuccess
            case "2":
            {
                return new RSPChooseJoinGameSuccess();
            }
            // RSPChooseSwitchGameFail
            case "3":
            {
                return new RSPChooseSwitchGameFail();
            }
            // RSPChooseSwitchGameSuccess
            case "4":{
                return new RSPChooseSwitchGameSuccess();
            }
            // RSPCommitSuccess
            case "5":{
                return new RSPCommitSuccess();
            }
            // RSPLoginFail
            case "6":{
                return new RSPLoginFail();
            }
            // RSPLoginSuccess
            case "7":{
                return new RSPLoginSuccess();
            }
            // RSPLogoutSuccess
            case "8":{
                return new RSPLogoutSuccess();
            }

            // RSPMoveFail
            case "9":{
                return new RSPMoveFail();
            }

            // RSPMoveSuccess
            case "10":{
                ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();
                ArrayList units = new ArrayList<>();

                arrayLists.add(units);
                return new RSPMoveSuccess("a1","a2",arrayLists,10);
            }

            // RSPNewGameSuccess
            case "11":{
                return new RSPNewGameSuccess();
            }

            // RSPOpenGameList
            case "12":{
                ArrayList<GameID> gameIDArrayList = new ArrayList<>();
                gameIDArrayList.add(new GameID(1));
                return new RSPOpenGameList(gameIDArrayList);
            }

            // RSPSignupFail
            case "13":{
                return new RSPSignupFail();
            }

            // RSPSignupSuccess
            case "14":{
                return new RSPSignupSuccess();
            }

            // RSPSwitchGameList
            case "15":{
                ArrayList<GameID> gameIDArrayList = new ArrayList<>();
                gameIDArrayList.add(new GameID(1));
                return new RSPSwitchGameList(gameIDArrayList);
            }

            // RSPUpdateTechFail
            case "16":{
                return new RSPUpgradeTechFail();
            }

            // RSPUpdateTechSuccess
            case "17":{
                return new RSPUpgradeTechSuccess();
            }
        }

        return null;
    }


    /**
     * Close socket
     * @throws IOException
     */
    public void close() throws IOException {
        this.serversocket.close();
    }


    public static void main(String[] args){
        MockServer mockServer = null;
        try {
            mockServer = new MockServer(1651);
            mockServer.acceptClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter a number to choose an action: ");
            String choose = reader.nextLine();

            try {
                assert mockServer != null;
                Object recvObj =  mockServer.recvObject();
                assert recvObj != null;
                System.out.println("Recv from client: " + recvObj.getClass().getName());
                Response r =  mockServer.chooseAction(choose);
                mockServer.sendObject(r);
                System.out.println("Send to client: " + r.getClass().getName());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }


}
