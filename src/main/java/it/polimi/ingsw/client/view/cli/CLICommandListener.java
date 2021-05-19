package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.events.send.*;
import it.polimi.ingsw.client.model.Board;
import it.polimi.ingsw.client.model.Marble;
import it.polimi.ingsw.client.utils.CommandListener;
import it.polimi.ingsw.client.utils.CommandListenerObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


/**
 * This class listens to inputs from the terminal
 */
public class CLICommandListener implements CommandListener {
    CommandListenerObserver commandListenerObserver;
    private final Scanner scanner = new Scanner(System.in);

    private static final int LEADER_CARDS_TO_CHOOSE = 2;
    private static final int MAX_MARKET_ARROW_ID = 6;
    private static final int MIN_MARKET_ARROW_ID = 1;


    protected void askCredentials() {

        System.out.println("Insert a nickname:");
        String nickname = scanner.nextLine();
        commandListenerObserver.setNickname(nickname);

        System.out.println("Insert a password:");
        String password = scanner.nextLine();

        notifyObservers(new LoginEvent(nickname, password));
    }

    protected void askNumberOfPlayers(String payload) {
        System.out.println("Choose number of players (" + payload + ") :");
        String numberOfPlayers = scanner.nextLine();
        try {
            notifyObservers(new SelectNumberPlayersEvent(Integer.parseInt(numberOfPlayers)));
        } catch (NumberFormatException e) {
            CLI.clearView();
            System.out.println(AsciiArts.RED + "Please re-insert a valid number" + AsciiArts.RESET);
            //askNumberOfPlayers(payload);
        }
    }

    protected void askSetupChoice(List<String> leaderCardsIDs, int numberOfResources) {

        List<Integer> chosenIndexes = askLeaderCardsChoice(leaderCardsIDs);
        if(chosenIndexes == null) {
            notifyObservers(new ChosenSetupEvent(null, null));
        } else {
            notifyObservers(new ChosenSetupEvent(chosenIndexes, askResourcesChoice(numberOfResources)));
        }

    }

    private List<Integer> askLeaderCardsChoice(List<String> leaderCardsIDs) {
        List<Integer> chosenIndexes = new ArrayList<>();
        for(String row : Board.getBoard().getMarketTray().getPrintable())
            System.out.println(row);

        //fixme indexes (now can choose 2 same indexes)
        for(int i = 0; i < LEADER_CARDS_TO_CHOOSE; i++) {
            System.out.println("Choose a " + AsciiArts.CYAN + "LeaderCard" + AsciiArts.RESET + ": ");
            for (int j = 0; j < leaderCardsIDs.size(); j++) {
                if(chosenIndexes.contains(j))
                    System.out.print(AsciiArts.GREEN_BACKGROUND + ">> " + "[" + j + "] : " + leaderCardsIDs.get(j) + " <<" + AsciiArts.RESET + "\t");
                else
                    System.out.print(AsciiArts.WHITE_BRIGHT + "[" + j + "]: " + AsciiArts.RESET + leaderCardsIDs.get(j) + "\t");
            }
            System.out.println();
            String choice = scanner.nextLine();
            try {
                chosenIndexes.add(Integer.parseInt(choice));
                leaderCardsIDs.get(Integer.parseInt(choice)); //used to trigger IndexOutOfBoundsException
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                CLI.clearView();
                System.out.println(AsciiArts.RED + "Please re-insert a valid number" + AsciiArts.RESET);
                return null;
            }
        }
        CLI.clearView();
        System.out.println(AsciiArts.GREEN + "Valid LeaderCards choices!" + AsciiArts.RESET);
        System.out.println(AsciiArts.WHITE_BOLD_BRIGHT + "Your LeaderCards: " + AsciiArts.RESET);
        for (Integer chosenIndex : chosenIndexes)
            System.out.println(leaderCardsIDs.get(chosenIndex));
        return chosenIndexes;
    }

    private List<String> askResourcesChoice(int numberOfResources) {

        for(String row : Board.getBoard().getMarketTray().getPrintable())
            System.out.println(row);

        List<Marble> storableMarbles = new ArrayList<Marble>(){{
            add(new Marble("YELLOW"));
            add(new Marble("GRAY"));
            add(new Marble("PURPLE"));
            add(new Marble("BLUE"));
        }};
        List<String> chosenResourcesColor = new ArrayList<>();

        if(numberOfResources == 0)
            return chosenResourcesColor;

        while (chosenResourcesColor.size() < numberOfResources){
            System.out.println("Choose a " + AsciiArts.CYAN + "resource" + AsciiArts.RESET + ": ");
            for (int j = 0; j < storableMarbles.size(); j ++) {
                System.out.print(AsciiArts.WHITE_BRIGHT + "[" + j + "]: " + AsciiArts.RESET + Marble.getAsciiMarbleByColor(storableMarbles.get(j).getColor()) + "\t\t");
            }
            System.out.println();
            String choice = scanner.nextLine();
            try {
                chosenResourcesColor.add(storableMarbles.get(Integer.parseInt(choice)).getColor());
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                CLI.clearView();
                System.out.println(AsciiArts.RED + "Please re-insert a valid number" + AsciiArts.RESET);
                return null;
            }
        }

        System.out.println(AsciiArts.GREEN + "Valid resources choices!" + AsciiArts.RESET);
        return chosenResourcesColor;
    }

    public void askCardPlacement(){
        int choice = -1;
        while(choice<1||choice>3){
            System.out.println("Select where you wish to place your new card(1-2-3)");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("invalid input");
            }
        }
        notifyObservers(new CardPlacementActionEvent(choice));
    }

    public void askResourceTransformation(int numberOfTransformation,List<String> possibleTransformations){
        List<String> transformations = new ArrayList<>();
        System.out.println("Looks like your white marbles are evolving\nChoose the color you prefer for this "+numberOfTransformation+" marbles");
        for (int i = 0; i < numberOfTransformation; i++) {
            System.out.print("Resource n" + (i + 1) + " can be: ");
            for(int j = 0;j<possibleTransformations.size();j++){
                String color = possibleTransformations.get(j);
                System.out.print((j+1)+Marble.getAsciiMarbleByColor(color.toUpperCase(Locale.ROOT))+" ");
            }
            System.out.print("\n");
            int choice = -1;
            while (choice<1||choice>possibleTransformations.size()){
                System.out.println("Select between 1 and 2");
                try {
                    choice=Integer.parseInt(scanner.nextLine());
                }catch (NumberFormatException e){
                    System.out.println("Not a number");
                }
            }
            transformations.add(possibleTransformations.get(choice-1));
        }
        notifyObservers(new TransformationActionEvent(transformations));
    }

    public String askFirstAction(){
        String answer = scanner.nextLine();
        while (!(answer.equals("market")||answer.equals("buy")||answer.equals("production")||answer.equals("see")||answer.equals("leader"))){
            System.out.println("invalid action, try again");
            answer = scanner.nextLine();
        }
        return answer;
    }

    public void askMarketAction(){
        int choice = -1;
        System.out.println("Select which row or column you want to take");
        while(choice<MIN_MARKET_ARROW_ID||choice>MAX_MARKET_ARROW_ID){
            try {
                choice = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("invalid input");
            }
        }
        notifyObservers(new MarketActionEvent(choice));
    }


    @Override
    public void notifyObservers(SendEvent sendEvent) {
        commandListenerObserver.update(sendEvent);
    }

    @Override
    public void registerObservers(CommandListenerObserver commandListenerObserver) {
        this.commandListenerObserver = commandListenerObserver;
    }
}
