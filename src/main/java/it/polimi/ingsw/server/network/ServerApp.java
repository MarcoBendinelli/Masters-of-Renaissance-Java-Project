package it.polimi.ingsw.server.network;

public class ServerApp {

    public static void main(String[] args) {
        //get only existing server(singleton) and start it
        Server server = Server.getServer();
        server.start();
    }
}
