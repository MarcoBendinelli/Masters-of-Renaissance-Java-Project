package it.polimi.ingsw.server.network.personal;

import it.polimi.ingsw.commons.Connection;
import it.polimi.ingsw.server.network.PongObserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class ServerConnection implements Connection {

    //todo correct implementation?
    private final BlockingQueue<String> messagesFromClient = new LinkedBlockingQueue<>();

    @Override
    public String getMessage() {
        String message = null;
        try {
            message = messagesFromClient.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            close(true);
        }
        return message;
    }

    /**
     * Add a new message to the messages from client queue.
     *
     * @param message to add
     */
    public void addMessageToQueue(String message){
        messagesFromClient.add(message);
    }

    public void clearStack(){}

    void setPongObserver(PongObserver pongObserver){}

}