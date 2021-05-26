package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.cli.Printable;

import java.util.ArrayList;
import java.util.List;

public class ProductionSlot extends DevelopmentCard{

    public ProductionSlot(String iD) {
        super(iD);
    }

    /**
     * This method return the print of a Production Card Slot
     *
     * @return a List composed by the lines of the Card
     */
    @Override
    public List<String> getPrintable() {
        List<String> developmentCardToPrint = new ArrayList<>();

        developmentCardToPrint.add("╔══════════╗");
        developmentCardToPrint.add("║Production║");
        developmentCardToPrint.add("║   Card   ║");
        developmentCardToPrint.add("║   Slot   ║");
        developmentCardToPrint.add("║          ║");
        developmentCardToPrint.add("║          ║");
        developmentCardToPrint.add("║          ║");
        developmentCardToPrint.add("║          ║");
        developmentCardToPrint.add("╚══════════╝");

        setWidth(developmentCardToPrint);
        return developmentCardToPrint;
    }

    /**
     * A Production Slot should not be placed on top of another card, so this method returns a Production Slot.
     *
     * @param oldCards The Printable object representing the cards on which this card is going to be placed
     * @return this
     */
    @Override
    public Printable placeOnAnotherCards(Printable oldCards) {
        return this;
    }
}