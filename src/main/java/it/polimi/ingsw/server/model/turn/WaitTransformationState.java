package it.polimi.ingsw.server.model.turn;

import it.polimi.ingsw.commons.enums.ResourcesEnum;
import it.polimi.ingsw.server.exceptions.InvalidEventException;
import it.polimi.ingsw.server.exceptions.NonStorableResourceException;
import it.polimi.ingsw.server.events.send.choice.ChoiceEvent;
import it.polimi.ingsw.server.events.send.choice.PlaceResourcesChoiceEvent;
import it.polimi.ingsw.server.events.send.graphics.GraphicUpdateEvent;
import it.polimi.ingsw.server.events.send.graphics.PersonalBoardUpdate;
import it.polimi.ingsw.server.events.send.graphics.WarehouseUpdate;
import it.polimi.ingsw.server.model.resources.ResourceFactory;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public class WaitTransformationState extends State {
    /**
     * Used to construct a turnLogic state waiting for the player to input his white resources transformation choices
     *
     * @param turnLogic turnLogic associated with the state
     */
    public WaitTransformationState(TurnLogic turnLogic) {
        super(turnLogic);
    }

    /**
     * Add the chosen resources for the white resource transformation to the warehouse's market zone
     * and set the current state of the game to WaitResourcePlacementState.
     *
     * @param chosenColors of the chosen resources
     * @return true if the chosen resources has been correctly created
     * @throws InvalidEventException if one of the chosen resource type doesn't exists
     * @throws NonStorableResourceException if one of the chosen resource is a NonStorableResource
     */
    @Override
    public boolean transformationAction(List<String> chosenColors) throws InvalidEventException, NonStorableResourceException {

        List<Resource> possibleTransformations = turnLogic.getWhiteResourcesFromMarket().get(0).getPossibleTransformations();

        if(chosenColors.size() != turnLogic.getWhiteResourcesFromMarket().size()){
            throw new InvalidEventException("Wrong number of chosen resources"); //wrong number of chosen resources
        }
        List<Resource> chosenResources = new ArrayList<>();
        for(String chosenColor : chosenColors) {
            try {
                ResourcesEnum chosenEnum = ResourcesEnum.valueOf(chosenColor.toUpperCase());
                //check that chosen color is one of the 2 expected
                if(possibleTransformations.stream().noneMatch(r -> r.getColor() == chosenEnum))
                    throw new InvalidEventException("Invalid resource type");
                else
                    chosenResources.add(ResourceFactory.produceResource(chosenEnum));
            } catch (IllegalArgumentException e) {
                throw new InvalidEventException("Non existing resource type"); //non existing resource type
            }
        }

        //add the chosen resources to the warehouse market zone
        turnLogic.getCurrentPlayer().getPersonalBoard().getWarehouse().addResourcesFromMarket(chosenResources);
        //send update of player warehouse
        GraphicUpdateEvent graphicUpdateEvent = new GraphicUpdateEvent();
        graphicUpdateEvent.addUpdate(new PersonalBoardUpdate(turnLogic.getCurrentPlayer(), new WarehouseUpdate()));
        graphicUpdateEvent.addUpdate(turnLogic.getCurrentPlayer().getNickname() + " transformed some White Marbles!");
        turnLogic.getModelInterface().notifyObservers(graphicUpdateEvent);
        //send placement event to client
        ChoiceEvent choiceEvent = new PlaceResourcesChoiceEvent(turnLogic.getCurrentPlayer().getNickname());
        turnLogic.setLastEventSent(choiceEvent);
        turnLogic.getModelInterface().notifyObservers(choiceEvent);
        turnLogic.setCurrentState(turnLogic.getWaitResourcePlacement());
        return true;
    }
}
