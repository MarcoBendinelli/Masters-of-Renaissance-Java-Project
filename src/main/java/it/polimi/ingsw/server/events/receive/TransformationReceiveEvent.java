package it.polimi.ingsw.server.events.receive;

import it.polimi.ingsw.exceptions.InvalidEventException;
import it.polimi.ingsw.exceptions.InvalidIndexException;
import it.polimi.ingsw.exceptions.NonStorableResourceException;
import it.polimi.ingsw.server.model.ModelInterface;

import java.util.List;

public class TransformationReceiveEvent extends ReceiveEvent {
    private final List<String> chosenResources;

    public TransformationReceiveEvent(String nickName, List<String> chosenResources) {
        super(nickName);
        this.chosenResources=chosenResources;
    }

    @Override
    public boolean doAction(ModelInterface modelInterface) throws InvalidIndexException, InvalidEventException, NonStorableResourceException {
        return modelInterface.transformationAction(chosenResources);
    }
}
