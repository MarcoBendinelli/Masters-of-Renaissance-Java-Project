package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.server.events.receive.ReceiveEvent;
import it.polimi.ingsw.server.events.send.SendEvent;
import it.polimi.ingsw.server.network.personal.PlayerData;
import it.polimi.ingsw.server.utils.Observable;
import it.polimi.ingsw.server.utils.Observer;

public class VirtualView implements Observer, Observable {
    private Observer controllerObserver;
    private final PlayerData playerData;

    public VirtualView(PlayerData playerData) {
        this.playerData = playerData;
        playerData.getClientHandler().setVirtualView(this);
    }

    @Override
    public void registerObserver(Observer observer) {
        this.controllerObserver = observer;
    }

    @Override
    public void removeObserver(Observer observer) {
    }

    @Override
    public void notifyObservers(ReceiveEvent receiveEventFromClient) {
        controllerObserver.update(receiveEventFromClient);
    }

    @Override
    public void update(SendEvent sendEvent) {
        if(sendEvent.getNickname().equals(playerData.getUsername())){
            playerData.getClientHandler().sendJsonMessage(sendEvent.toJson());
        }

        //check if player is owner of this virtual view
        //if yes send serializable event with data to client
    }

    /**
     * Not used.
     *
     *
     * metodo inutilizzato
     * view non riceverà mai send event da mandare al controller : possono partire solo dal model,
     * metodo chiamato dal modelInterface
     *
     * @param sendEvent null
     */
    @Override
    public void notifyObservers(SendEvent sendEvent) {
    }

    /**
     * Not used
     *
     * view non verrà mai notificata con receive event perché possono partire solo da lei
     *
     * @param receiveEvent null
     */
    @Override
    public void update(ReceiveEvent receiveEvent) {
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}