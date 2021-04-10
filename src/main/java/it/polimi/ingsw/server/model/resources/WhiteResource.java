package it.polimi.ingsw.server.model.resources;

import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.enums.ResourceEnum;
import it.polimi.ingsw.server.model.turn.TurnLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Resource that can be transformed in one of the possibleTransformations resource and
 * that can't be used to make a production by the ProductionCard cards.
 * For this reason the productionAbility is not redefined.
 */
public class WhiteResource extends Resource {

    public WhiteResource() {
        super(ResourceEnum.WHITE);
    }

    /**
     * Possible transformations of this WhiteResource provided by the active TransformationLeaderCard cards
     * owned by the current player.
     */
    private List<Resource> possibleTransformations = new ArrayList<>();


    /**
     * Method to call after a Resource has been chosen in the MarketTray.
     * Check if there are some possible transformations of this WhiteResource provided by
     * the active TransformationLeaderCard cards owned by the current player.
     * If yes save them in possibleTransformations calling doTransformation() offered by the LeaderCard cards.
     * @param turn  containing the current player, the current state of the game and others information
     * @return true if there are some possible transformations of this WhiteResource
     */
    @Override
    public boolean marketAbility(TurnLogic turn){
        int activeTransformationLeaderCard = 0;
        //TODO
        //for(LeaderCard leaderCard : turn.currentPlayer.getActiveLeaderCards())
        //    if(leaderCard.doTransformation(this))
                //activeTransformationLeaderCard++;
        return activeTransformationLeaderCard > 0;
    }

    /**
     * Add a possible transformation of this WhiteResource provided by a TransformationLeaderCard activated by
     * the current player.
     * @param transformation that this WhiteResource can take
     * @return true if the transformation has been correctly added to the possibleTransformations list
     */
    public boolean addPossibleTransformation(Resource transformation){
        return this.possibleTransformations.add(transformation);
    }
}