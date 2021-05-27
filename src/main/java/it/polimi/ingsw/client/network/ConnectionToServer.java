package it.polimi.ingsw.client.network;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionToServer implements Runnable {
    private final static String PONG_MESSAGE = "pong";
    private final static String QUIT_TYPE = "quit";
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private final BlockingQueue<String> messagesFromServer = new LinkedBlockingQueue<>();
    private boolean receivedPing;
    private Timer timer;
    //must be higher than the ping period
    private final static int TIMER_DELAY = 6000;//in milliseconds
    private final static String PING_MESSAGE = "ping";

    public ConnectionToServer(Socket socket) {
        this.socket = socket;
        startConnection();
        this.receivedPing = false;
        this.timer = new Timer();
    }

    private void startConnection() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Failed to start connection with server");
            e.printStackTrace();
            close();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getMessage() {
        String message = null;
        try {
            message = messagesFromServer.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            close();
        }
        return message;
    }

    public void close() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", QUIT_TYPE);
            sendMessage(jsonObject.toString());
            socket.close();
            in.close();
            out.close();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("failed to close socket");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void sendPong() {
        out.println(PONG_MESSAGE);
    }

    @Override
    public void run() {
        String message;
        while (true) {
            try {
                message = in.readLine();
                if (message.equals(PING_MESSAGE)) {
                    handlePing();
                } else if(message.equals(QUIT_TYPE)){
                    close();
                }else {
                    messagesFromServer.add(message);
                }
            } catch (IOException e) {
                message = null; //todo: socket failed
            }
        }
    }

    /**
     * Immediately respond to the server with a pong message and start a timer to recognize if server is down
     * It does so by setting up a timer with a delay bigger than the expected ping pong system period
     * When finished the timer checks that a new ping message was received and if it is missing the client is closed
     */
    private void handlePing() {
        receivedPing = true;
        this.sendPong();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (receivedPing) {
                    receivedPing = false;
                } else {
                    //todo add client closing code
                    System.out.println("Server is unreachable");
                    close();
                }
            }
        }, TIMER_DELAY);
    }
}