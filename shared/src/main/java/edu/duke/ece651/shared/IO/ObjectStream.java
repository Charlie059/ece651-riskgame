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
    public boolean sendObject(Object object) {
        try {
            this.objectOutputStream.writeObject(object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Receive object
     * @return object or null
     */
    public Object recvObject(){
        try{
           return this.objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
