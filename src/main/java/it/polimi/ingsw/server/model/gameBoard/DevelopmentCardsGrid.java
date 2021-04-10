package it.polimi.ingsw.server.model.gameBoard;

import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.CardsGenerator;
import it.polimi.ingsw.server.model.enums.CardColorEnum;
import java.util.*;

public class DevelopmentCardsGrid {
    private static final int numOfLevels = 3;
    private static DevelopmentCardsGrid instance = null;
    private List<Map<CardColorEnum,List<DevelopmentCard>>> mapByLevel;
    private final List<DevelopmentCard> developmentCards;
    private final CardsGenerator generator = new CardsGenerator();

    /**
     * reset for testing being a singleton class
     */
    protected void reset(){
        mapByLevel=new ArrayList<>();
        for (int i = 1;i<=numOfLevels;i++){
            mapByLevel.add(generator.getDevCardsAsGrid(developmentCards,i));
        }
    }

    private DevelopmentCardsGrid(){
        developmentCards=generator.generateDevelopmentCards();
        shuffle(developmentCards);
        mapByLevel=new ArrayList<>();
        for (int i = 1;i<=numOfLevels;i++){
            mapByLevel.add(generator.getDevCardsAsGrid(developmentCards,i));
        }
    }

    /**
     * Create an instance of DevelopmentCardsGrid or return the existing one
     * @return the only existing instance of DevelopmentCardsGrid
     */
    public static synchronized DevelopmentCardsGrid getDevelopmentCardsGrid(){
        if(instance== null){
            instance = new DevelopmentCardsGrid();
        }
        return instance;
    }

    /**
     * shuffle the List</developmentCard> given
     * @param cards List of cards to shuffle
     */
    private boolean shuffle(List<DevelopmentCard> cards){
        Collections.shuffle(cards);
        return true;
    }

    /**
     * Create a list of all the cards in the grid visible for the player
     * @return the List of available devCards
     */
    //todo should this function throw an exception when the grid is empty or return an empty array?
    public List<DevelopmentCard> getAvailableCards(){
        List<DevelopmentCard> toReturn = new ArrayList<>();
        mapByLevel.forEach((element)->element.forEach((key,value)-> {
                if (value.size() != 0) {
                    toReturn.add(value.get(0));
                }
        }));
        return toReturn;
    }

    /**
     * Remove the lowest level card of the given color
     * @param color color of the card to remove
     * @return true if there was at least one card of that color in the grid
     */
    public boolean removeCardByColor(CardColorEnum color){
        //for and not foreach because foreach can't be interrupted
        for (Map<CardColorEnum,List<DevelopmentCard>> m:mapByLevel){
            if(m.get(color).size()!=0){
                m.get(color).remove(0);
                return true;
            }
        }
        return false;
    }


    /**
     * Check if all the cards of at least one color have been removed
     * @return true if one color has no more cards
     */
    public boolean hasEmptyColumn(){
        for(CardColorEnum color:EnumSet.allOf(CardColorEnum.class)) {
            boolean empty = true;
            for (Map<CardColorEnum, List<DevelopmentCard>> m : mapByLevel) {
                if (m.get(color).size() != 0) {
                    empty=false;
                    break;
                }
            }
            if(empty) {
                return true;
            }
        }
        return false;
    }
}