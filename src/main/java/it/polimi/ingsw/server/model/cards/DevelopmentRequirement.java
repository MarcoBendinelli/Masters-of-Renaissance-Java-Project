package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.enums.CardColorEnum;
import it.polimi.ingsw.server.model.player.Player;

public class DevelopmentRequirement implements Requirement {
    private final int level;
    private final CardColorEnum color;
    private final int quantity;

    public DevelopmentRequirement(int level, CardColorEnum color, int quantity) {
        this.level = level;
        this.color = color;
        this.quantity=quantity;
    }

    /**
     * Check if the condition is satisfied by the player
     * @param player player to check
     * @return true if satisfied
     */
    @Override
    public boolean isSatisfied(Player player) {
        //TODO add verification code
        return false;
    }
}