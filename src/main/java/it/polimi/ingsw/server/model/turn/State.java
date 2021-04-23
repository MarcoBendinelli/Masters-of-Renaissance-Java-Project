package it.polimi.ingsw.server.model.turn;

import it.polimi.ingsw.exceptions.*;

import java.util.List;
import java.util.Map;

public abstract class State {
    TurnLogic turnLogic;
    public State(TurnLogic turnLogic){this.turnLogic=turnLogic;}

    public boolean marketAction(int arrowID) throws InvalidEventException, InvalidIndexException {throw new InvalidEventException();}
    public boolean productionAction(Map<Integer, List<Integer>> productionMapIN, Map<Integer, String> productionMapOUT) throws InvalidEventException, InvalidIndexException, NonStorableResourceException, EmptySlotException, NonAccessibleSlotException { throw new InvalidEventException(); }
    public boolean buyAction(String cardColor, int cardLevel, List<Integer> resourcesPositions) throws InvalidEventException, InvalidIndexException, EmptySlotException, NonAccessibleSlotException {throw new InvalidEventException();}
    public boolean leaderAction(String ID,boolean discard) throws InvalidEventException {throw new InvalidEventException();}
    public boolean placeResourceAction(List<Integer> swapPairs) throws InvalidEventException, InvalidIndexException, EmptySlotException, NonAccessibleSlotException {throw new InvalidEventException();}
    public boolean transformationAction(List<String> chosenColors) throws InvalidEventException, NonStorableResourceException {throw new InvalidEventException();}
    public boolean placeDevCardAction(int slotPosition) throws InvalidEventException {throw new InvalidEventException();}
    public boolean endTurn() throws InvalidEventException {throw new InvalidEventException();}
}
