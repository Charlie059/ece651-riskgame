package edu.duke.ece651.shared.IO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectStream {
    private Socket clientSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    public ObjectStream(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.objectOutputStream = new
                ObjectOutputStream(clientSocket.getOutputStream());
        this.objectInputStream = new
                ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * Send Object to Client
     * @param object The Object ready to send
     * @return true: successfully; false: IO exception, commit
     */
    public boolean sendObject(Object object) throws IOException {
            this.objectOutputStream.writeObject(object);
//            this.objectOutputStream.flush();
            return true;
    }

    /**
     * Receive object
     * @return object or null
     */
    public Object recvObject() throws IOException, ClassNotFoundException {
        return this.objectInputStream.readObject();
    }


}
