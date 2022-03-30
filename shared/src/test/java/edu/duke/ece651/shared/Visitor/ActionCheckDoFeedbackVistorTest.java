package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.IO.*;
import edu.duke.ece651.shared.IO.ClientActions.Action;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


class ActionCheckDoFeedbackVistorTest {


    public class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final MockServer mockServer;
        private Response response;
        private Action action;
        public ClientHandler(Socket socket, MockServer mockServer, Response response, Action action) {
            this.clientSocket = socket;
            this.mockServer = mockServer;
            this.response = response;
            this.action = action;
        }

        public void run() {
            try {
                this.mockServer.sendObject(this.response);
                this.action = (Action) this.mockServer.recvObject();
                this.action.accept(new ActionCheckDoFeedbackVistor(this.clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private helper() throws IOException {
        MockServer mockServer = new MockServer(4444);
        MockClient mockClient = new MockClient(4444, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        ClientHandler task = new ClientHandler(clientSocket, mockServer);
        new Thread(task).start();

        MockClass recvObj = (MockClass) mockClient.recvObject();
        assertEquals(recvObj.getA(), 2);
        clientSocket.close();

        ObjectStream objectStream = new ObjectStream();


    }

    @Test
    void visit() {

    }

    @Test
    void testVisit() {
    }

    @Test
    void testVisit1() {
    }

    @Test
    void testVisit2() {
    }

    @Test
    void testVisit3() {
    }

    @Test
    void testVisit4() {
    }

    @Test
    void testVisit5() {
    }

    @Test
    void testVisit6() {
    }

    @Test
    void testVisit7() {
    }

    @Test
    void testVisit8() {
    }

    @Test
    void testVisit9() {
    }

    @Test
    void testVisit10() {
    }

    @Test
    void testVisit11() {
    }

    @Test
    void testVisit12() {
    }
}