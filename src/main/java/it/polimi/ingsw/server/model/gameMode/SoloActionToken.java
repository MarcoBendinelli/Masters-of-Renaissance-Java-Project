package it.polimi.ingsw.server.model.gameMode;

import it.polimi.ingsw.server.model.gameBoard.GameBoard;

/**
 * Interface that represents the general type Token
 */
public interface SoloActionToken {

    /**
     * Method that implements the action of the token
     * @param gameBoard is used to increment the Lorenzo's Faith Track
     * @param lorenzo is used to access the Lorenzo's Faith Track
     * @return true if the action is did by the class SingleFaithTrackProgress
     */
    boolean doAction(GameBoard gameBoard, Lorenzo lorenzo);
}