package it.polimi.ingsw.client.view.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.model.DevelopmentCardsGrid;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.gui.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GUI extends Application implements View {

    private NetworkHandler networkHandler;
    private String nickname;
    private final Map<String, GUICommandListener> guiCommandListeners = new HashMap<String, GUICommandListener>() {{
        put("loginController", new LoginController());
        put("chooseNumberController", new ChooseNumberController());
        put("marketController", new MarketController());
        put("setupController",new SetupController());
    }};
    private GUICommandListener currentGuiCommandListener;
    private static Scene scene;

    public void setGUI(String ip, int port) throws IOException {
        try {
            this.networkHandler = new NetworkHandler(ip, port, this);
            guiCommandListeners.values().forEach(guiCommandListener -> guiCommandListener.registerObservers(networkHandler));
            new Thread(this::startNetwork).start();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void startNetwork() {
        networkHandler.startNetwork();
    }


    @Override
    public void graphicUpdate() {

    }

    @Override
    public void printInfoMessage(String info) {
        currentGuiCommandListener.printInfoMessage(info);
    }

    @Override
    public void printErrorMessage(String error) {
        currentGuiCommandListener.printErrorMessage(error);
    }

    @Override
    public void setOnLogin() {
        GUICommandListener nextGuiCommandListener = guiCommandListeners.get("loginController");
        setRoot("loginScene", nextGuiCommandListener);
        currentGuiCommandListener = nextGuiCommandListener;
    }

    @Override
    public void setOnChooseNumberOfPlayers(String payload) {
        GUICommandListener nextGuiCommandListener = guiCommandListeners.get("chooseNumberController");
        setRoot("chooseNumberScene", nextGuiCommandListener);
        currentGuiCommandListener = nextGuiCommandListener;
    }

    @Override
    public void setOnMatchMaking() {
        //todo move this code in place were marketAction is selected
        /*
        GUICommandListener nextGuiCommandListener = guiCommandListeners.get("marketController");
        setRoot("marketScene", nextGuiCommandListener);
        currentGuiCommandListener = nextGuiCommandListener;
         */
    }

    @Override
    public void setOnSetup(List<String> leaderCardsID, int numberOfResource) {
        SetupController nextGuiCommandListener = (SetupController) guiCommandListeners.get("setupController");
        nextGuiCommandListener.initializeData(leaderCardsID,numberOfResource);
        setRoot("setupScene", nextGuiCommandListener);
        currentGuiCommandListener = nextGuiCommandListener;
    }

    @Override
    public void setOnYourTurn() {
        GUICommandListener nextGuiCommandListener = guiCommandListeners.get("marketController");
        setRoot("marketScene", nextGuiCommandListener);
        currentGuiCommandListener = nextGuiCommandListener;
    }

    @Override
    public void setOnWaitForYourTurn(String currentPlayer) {

    }

    @Override
    public void setOnDevelopmentCardPlacement(String newCardID) {

    }

    @Override
    public void setOnResourcesPlacement() {

    }

    @Override
    public void setOnTransformation(int numberOfTransformation, List<String> possibleTransformations) {

    }

    @Override
    public void setOnEndTurn() {

    }

    @Override
    public void setOnEndGame(String winner, Map<String, Integer> playersPoints) {

    }

    @Override
    public void start(Stage stage) throws Exception {
        currentGuiCommandListener = new WelcomeController(this);
        scene = new Scene(Objects.requireNonNull(loadFXML("welcomeScene", currentGuiCommandListener)), 800, 800);
        stage.setTitle("Maestri del Rinascimento");
        stage.setScene(scene);
        stage.show();
    }

    private Parent loadFXML(String fxmlFileName, GUICommandListener guiCommandListener) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/fxmls/" + fxmlFileName + ".fxml"));
        fxmlLoader.setController(guiCommandListener);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setRoot(String fxml, GUICommandListener guiCommandListener) {
        if (!guiCommandListener.equals(currentGuiCommandListener))
            scene.setRoot(loadFXML(fxml, guiCommandListener));
    }

    public void show() {
        launch();
    }
}
