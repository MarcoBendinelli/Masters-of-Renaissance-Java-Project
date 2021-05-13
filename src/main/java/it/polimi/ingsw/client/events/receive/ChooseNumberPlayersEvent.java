package it.polimi.ingsw.client.events.receive;

import it.polimi.ingsw.client.view.View;

public class ChooseNumberPlayersEvent implements ReceiveEvent{

    private final String payload;

    public ChooseNumberPlayersEvent(String payload) {
        this.payload = payload;
    }

    @Override
    public void updateView(View view) {
        view.setOnChooseNumberOfPlayers(payload);
    }
}