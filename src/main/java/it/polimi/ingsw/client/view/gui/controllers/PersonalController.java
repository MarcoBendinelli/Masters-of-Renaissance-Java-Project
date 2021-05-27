package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.events.send.MarketActionEvent;
import it.polimi.ingsw.client.model.Board;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.GUICommandListener;
import it.polimi.ingsw.client.view.gui.GraphicUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PersonalController extends GUICommandListener {
    private Stage leaderHandWindow;
    private String nickname;
    private HandController handController;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView extraRes;
    @FXML
    private GridPane marketGrid;
    @FXML
    private GridPane devGrid;
    @FXML
    private Button handButton;
    @FXML
    private HBox HActiveLeaders;
    @FXML
    private VBox VArrowButtons;
    @FXML
    private HBox HArrowButtons;
    @FXML
    private HBox HResFromMarket;
    @FXML
    private VBox HLeadersRes;
    @FXML
    private GridPane strongboxGrid;
    @FXML
    private Button buyCard;
    @FXML
    private Button endProduction;
    @FXML
    private Button endTurn;
    @FXML
    private AnchorPane warehouse;
    @FXML
    private AnchorPane productionPane;

    @FXML
    private void initialize() {
        //Load proper grids data
        GraphicUtilities.populateMarket(marketGrid, extraRes);
        GraphicUtilities.populateDevGrid(devGrid);
        GraphicUtilities.populateLeaders(HActiveLeaders, Board.getBoard().getPersonalBoardOf(nickname).getActiveLeaders());
        //Prepare action for seeLeaderHand button
        handButton.setOnMousePressed(event -> showHandAction());
        //Prepare actions for market buttons
        for (Node b : Stream.concat(HArrowButtons.getChildren().stream(), VArrowButtons.getChildren().stream()).collect(Collectors.toList())) {
            Button button = (Button) b;
            button.setOnMousePressed((event -> marketAction(button.getText())));
        }
        handController = new HandController(nickname);
        handController.registerObservers(getCommandListenerObserver());
        for (Node n : marketGrid.getChildren()) {
            n.setOnMousePressed(event -> handleMarketRequest(n));
        }
        for (Node n : devGrid.getChildren()) {
            n.setOnMousePressed(event -> handleBuyRequest(n));
        }
        //setAllResources buttons ID as their indexes
        int i = 0;
        for(Node n:HResFromMarket.getChildren()){
            n.setId(String.valueOf(i));
            n.setOnMousePressed(event -> resourceClick(n.getId()));
            i++;
        }
        for(Node n:warehouse.getChildren()){
            n.setId(String.valueOf(i));
            n.setOnMousePressed(event -> resourceClick(n.getId()));
            i++;
        }
        for(Node n:HLeadersRes.getChildren()){
            n.setId(String.valueOf(i));
            n.setOnMousePressed(event -> resourceClick(n.getId()));
            i++;
        }
        for(Node n:strongboxGrid.getChildren()){
            n.setId(String.valueOf(i));
            n.setOnMousePressed(event -> resourceClick(n.getId()));
            i++;
        }
        //set all productionBoard indexes
        i = 0;
        for(Node n:productionPane.getChildren()){
            n.setId(String.valueOf(i));
            n.setOnMousePressed(event -> productionClick(n.getId()));
            i++;
        }

    }
    private void showHandAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/fxmls/leaderHandScene.fxml"));
        fxmlLoader.setController(handController);
        leaderHandWindow = GraphicUtilities.populatePopupWindow(mainPane.getScene().getWindow(), fxmlLoader, leaderHandWindow, Modality.WINDOW_MODAL);
        leaderHandWindow.show();
    }

    private void marketAction(String arrowID) {
        notifyObservers(new MarketActionEvent(Integer.parseInt(arrowID)));
    }

    private void handleMarketRequest(Node n) {
        n.setVisible(false);
        System.out.println("buy :)");
    }

    private void handleBuyRequest(Node n) {
        Button button = (Button) n;
        System.out.println(button.getId());
    }
    private void resourceClick(String resID){
        System.out.println(resID);
    }
    private void productionClick(String prodID){
        System.out.println(prodID);
    }

}