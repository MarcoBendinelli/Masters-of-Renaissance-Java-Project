package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.events.send.CardPlacementActionEvent;
import it.polimi.ingsw.client.model.Board;
import it.polimi.ingsw.client.view.gui.GUICommandListener;
import it.polimi.ingsw.client.view.gui.GraphicUtilities;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class CardPlacementController extends GUICommandListener {
    private final String nickname;
    private String newCardID;

    public CardPlacementController(String nickname) {
        this.nickname = nickname;
    }

    public void setNewCardID(String newCardID) {
        this.newCardID = newCardID;
    }

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView newCard;

    @FXML
    private AnchorPane personalBoard;

    @FXML
    private void initialize() {
        File file = new File("src/main/resources/images/devCards/" + newCardID.toLowerCase(Locale.ROOT) + ".png");
        newCard.setImage(new Image(file.toURI().toString()));
        GraphicUtilities.populateProductionBoard(personalBoard, nickname);
        int i = 0;
        for(Node slot : personalBoard.getChildren()) {
            slot.setId(String.valueOf(i));
            slot.setOnMousePressed(mouseEvent -> placeCardAction(slot));
            i++;
        }
    }

    private void placeCardAction(Node slot) {
        System.out.println(slot.getId());
        notifyObservers(new CardPlacementActionEvent(Integer.parseInt(slot.getId())));
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

}
