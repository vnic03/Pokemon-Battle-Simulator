package org.example.Gui.battleScene;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Gui.TeamBuilder;
import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;
import org.example.teams.Team;

import java.util.*;

public class BattleView extends AnchorPane {
    private BattleLogic battleLogic;
    private Team team1;
    private Team team2;
    private HBox team1Container = new HBox(10);
    private HBox team2Container = new HBox(10);
    private VBox playerOneView = new VBox(10);
    private VBox playerTwoView = new VBox(10);
    private TextArea battleLog = new TextArea();
    private ContextMenu switchMenu;
    private Button switchButtonTeam1;
    private Button switchButtonTeam2;
    private HBox fullLayoutTeam1;
    private HBox fullLayoutTeam2;
    private VBox movesLayoutTeam1;
    private VBox movesLayoutTeam2;
    private Label timerLabelTeam1;
    private Label timerLabelTeam2;
    private HashMap<Pokemon, PokemonStatusBar> statusBarMap = new HashMap<>();

    public BattleView(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;

        initializeBattleField();
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/battleViewStyles/battleViewStyle.css")).toExternalForm());
        this.getStyleClass().add("battle-view");
    }

    private void initializeBattleField() {

        team1Container.setAlignment(Pos.CENTER_LEFT);
        team2Container.setAlignment(Pos.CENTER_RIGHT);

        fullLayoutTeam1 = new HBox();
        fullLayoutTeam2 = new HBox();

        movesLayoutTeam1 = new VBox(20);
        movesLayoutTeam2 = new VBox(20);

        timerLabelTeam1 = new Label("60");
        timerLabelTeam1.getStyleClass().add("timer-label");
        AnchorPane.setTopAnchor(timerLabelTeam1, 200.0);
        AnchorPane.setLeftAnchor(timerLabelTeam1, 200.0);

        timerLabelTeam2 = new Label("60");
        timerLabelTeam2.getStyleClass().add("timer-label");
        AnchorPane.setTopAnchor(timerLabelTeam2, 10.0);
        AnchorPane.setRightAnchor(timerLabelTeam2, 200.0);

        playerOneView.getChildren().addAll(team1Container, fullLayoutTeam1, movesLayoutTeam1);
        playerTwoView.getChildren().addAll(team2Container, fullLayoutTeam2, movesLayoutTeam2);

        AnchorPane.setBottomAnchor(playerOneView, 150.0);
        AnchorPane.setLeftAnchor(playerOneView, 200.0);
        AnchorPane.setTopAnchor(playerTwoView, 150.0);
        AnchorPane.setRightAnchor(playerTwoView, 300.0);

        this.getChildren().addAll(playerOneView, playerTwoView, timerLabelTeam1, timerLabelTeam2);
        initializeBattleLog();
    }

    public void loadTeams(Team team1, Team team2) {
        team1Container.getChildren().clear();
        team2Container.getChildren().clear();

        updateLayoutForTeam(team1, team1Container, fullLayoutTeam1, movesLayoutTeam1 ,true);
        updateLayoutForTeam(team2, team2Container, fullLayoutTeam2, movesLayoutTeam2 ,false);
    }

    private void changePokemon(Team team, int pokemonIndex, boolean isTeamOne) {
        if (pokemonIndex < 0 || pokemonIndex >= team.getPokemons().size()) {
            return;
        }
        Pokemon newPokemon = team.getPokemons().get(pokemonIndex);
        if (isTeamOne) {
            ImageView newSpriteView = new ImageView(newPokemon.getBackSprite());
            newSpriteView.setFitWidth(200);
            newSpriteView.setFitHeight(200);
            newSpriteView.setPreserveRatio(true);
            newSpriteView.setSmooth(true);
            PokemonStatusBar newStatusBar = new PokemonStatusBar(newPokemon);

            team1Container.getChildren().clear();
            team1Container.getChildren().addAll(newSpriteView, newStatusBar);
        } else {
            ImageView newSpriteView = new ImageView(newPokemon.getFrontSprite());
            newSpriteView.setFitWidth(200);
            newSpriteView.setFitHeight(200);
            newSpriteView.setPreserveRatio(true);
            newSpriteView.setSmooth(true);
            PokemonStatusBar newStatusBar = new PokemonStatusBar(newPokemon);

            team2Container.getChildren().clear();
            team2Container.getChildren().addAll(newStatusBar, newSpriteView);
        }
        PokemonStatusBar statusBar = statusBarMap.get(newPokemon);
        if (statusBar == null) {
            statusBar = new PokemonStatusBar(newPokemon);
            statusBarMap.put(newPokemon, statusBar);
        }
    }

    public void handlePokemonChangeRequest(Team team, int pokemonIndex, boolean isTeam1) {
        if (canPokemonSwitch(team.getPokemons().get(pokemonIndex))) {

            changePokemon(team, pokemonIndex, isTeam1);
            hideButtons(isTeam1);
            team.setActivePokemonIndex(pokemonIndex);
            updateTeamView(isTeam1 ? team1Container : team2Container, team, isTeam1);

            List<Button> movesButtons = createMovesButtonForTeam(team.getPokemons().get(pokemonIndex));
            Button switchButton = createSwitchButton(team, isTeam1);
            updateMovesLayout(movesButtons, switchButton, isTeam1 ? movesLayoutTeam1 : movesLayoutTeam2);
        } else {
            System.out.println("Can't switch right now"); // fix later
        }
    }
    private void updateTeamView(HBox teamContainer, Team team, boolean isTeam1) {
        teamContainer.getChildren().clear();

        Pokemon activePokemon = team.getPokemons().get(team.getActivePokemonIndex());
        if (activePokemon != null) {
            ImageView spriteView = new ImageView(isTeam1 ? activePokemon.getBackSprite() : activePokemon.getFrontSprite());
            spriteView.setFitWidth(200);
            spriteView.setFitHeight(200);
            spriteView.setPreserveRatio(true);
            spriteView.setSmooth(true);
            PokemonStatusBar statusBar = new PokemonStatusBar(activePokemon);
            teamContainer.getChildren().addAll(spriteView, statusBar);
        }
    }

   private Button createSwitchButton(Team team, boolean isTeam1) {
       if ((isTeam1 && switchButtonTeam1 != null) || (!isTeam1 && switchButtonTeam2 != null)) {
           return isTeam1 ? switchButtonTeam1 : switchButtonTeam2;
       }
       Button switchButton = new Button("Switch Pokemon");
       switchButton.getStyleClass().add("switch-button");

       switchButton.setOnAction(event -> {
           if (switchMenu == null) {
               switchMenu = new ContextMenu();
           } else {
               switchMenu.getItems().clear();
           }
           int activePokemonIndex = team.getActivePokemonIndex();

           for (int i = 0; i < team.getPokemons().size(); i++) {
               Pokemon pokemon = team.getPokemons().get(i);
               if (pokemon != null && i != activePokemonIndex) {
                   MenuItem menuItem = new MenuItem(pokemon.getName() + " - HP: " + pokemon.getStats().getHp());

                   Image pokemonImage = new Image(pokemon.getIconSprite().getUrl());
                   ImageView pokemonIconView = TeamBuilder.createPokemonIconView(pokemonImage);
                   pokemonIconView.setFitWidth(40);
                   pokemonIconView.setFitHeight(40);

                   menuItem.setGraphic(pokemonIconView);

                   int final_I = i;
                   menuItem.setOnAction(innerEvent -> handlePokemonChangeRequest(team, final_I, isTeam1));
                   switchMenu.getItems().add(menuItem);
               }
           }
           switchMenu.show(switchButton, Side.RIGHT, 0, 0);
       });
       if (isTeam1) {
           switchButtonTeam1 = switchButton;
       } else {
           switchButtonTeam2 = switchButton;
       }
       return switchButton;
   }
    private boolean canPokemonSwitch(Pokemon pokemon) {
        // add logic later
        return true;
    }

    private List<Button> createMovesButtonForTeam(Pokemon activePokemon) {
        List<Button> moveButtons = new ArrayList<>();

        if (activePokemon != null) {
            for (Moves move : activePokemon.getMoves()) {
                Button moveButton = new Button(move.getName() + " / pp." + move.getCurrentPP());
                moveButton.getStyleClass().addAll("move-button", move.getType().toString());
                moveButton.setOnAction(event -> handleMoveSelection(move, activePokemon));
                moveButtons.add(moveButton);
            }
        }
        return moveButtons;
    }
    private void handleMoveSelection(Moves move, Pokemon activePokemon) {
        boolean isTeam1 = activePokemon.belongsTo(team1);
        Team otherTeam = isTeam1 ? team2 : team1;

        battleLogic.receivedMoveSelection(move, isTeam1 ? team1 : team2);

        if (battleLogic.areMovesSelected(isTeam1)) {
            hideButtons(isTeam1);
        }

        Pokemon opponent = otherTeam.getActivePokemon();
        PokemonStatusBar opponentStatusBar = statusBarMap.get(opponent);

        if (opponentStatusBar != null) {
            opponentStatusBar.updateHp(opponent.getStats().getHp(), opponent.getStats().getMaxHp());
        }
    }

    private void updateUIWithBattleResult(BattleRoundResult result) {

    }

    private void initializeBattleLog() {
        battleLog.setEditable(false);
        battleLog.setWrapText(true);
        battleLog.getStyleClass().add("battle-log");
        battleLog.setPrefSize(600, 90);

        AnchorPane.setBottomAnchor(battleLog, 10.0);
        AnchorPane.setRightAnchor(battleLog, 100.0);
        this.getChildren().add(battleLog);
    }

    public void updateBattlelog(BattleRoundResult result) {
        String currentText = battleLog.getText();
        battleLog.setText(currentText + "\n" + result.getMessage());

        battleLog.setScrollTop(Double.MAX_VALUE);
    }
    public void setBattleLogic(BattleLogic battleLogic) {
        this.battleLogic = battleLogic;
    }

    private void updateMovesLayout(List<Button> moveButtons, Button switchButton, VBox movesLayout) {
        movesLayout.getChildren().clear();
        HBox topRow = new HBox(10);
        HBox bottomRow = new HBox(10);

        for (int i = 0; i < moveButtons.size(); i++) {
            if (i < 2) {
                topRow.getChildren().add(moveButtons.get(i));
            } else {
                bottomRow.getChildren().add(moveButtons.get(i));
            }
        }
        movesLayout.getChildren().addAll(switchButton, topRow, bottomRow);
        movesLayout.setAlignment(Pos.CENTER);
    }
    private void updateLayoutForTeam(Team team, HBox teamContainer, HBox fullLayout,VBox movesLayout, boolean isTeam1) {
        teamContainer.getChildren().clear();
        fullLayout.getChildren().clear();
        movesLayout.getChildren().clear();

        if (!team.getPokemons().isEmpty()) {
            Pokemon pokemon = team.getPokemons().getFirst();
            if (pokemon != null) {
                ImageView spriteView = new ImageView(isTeam1 ? pokemon.getBackSprite() : pokemon.getFrontSprite());
                spriteView.setFitWidth(200);
                spriteView.setFitHeight(200);
                spriteView.setPreserveRatio(true);
                spriteView.setSmooth(true);
                PokemonStatusBar statusBar = new PokemonStatusBar(pokemon);
                statusBarMap.put(pokemon, statusBar);
                teamContainer.getChildren().addAll(spriteView, statusBar);

                List<Button> moveButtons = createMovesButtonForTeam(pokemon);
                Button switchButton = createSwitchButton(team, isTeam1);

                updateMovesLayout(moveButtons, switchButton, movesLayout);

                fullLayout.getChildren().addAll(movesLayout, teamContainer);
            }
        }
    }
    public void updateTimerTeam1(int remainingTime) {
        Platform.runLater(() -> {
            timerLabelTeam1.setText(String.valueOf(remainingTime));
        });
    }
    public void updateTimerTeam2(int remainingTime) {
        Platform.runLater(() -> {
            timerLabelTeam2.setText(String.valueOf(remainingTime));
        });
    }
    public void showNewRoundStarted() {
        battleLog.appendText("-"); // fix later
    }
    public void hideButtons(boolean isTeam1) {
        VBox movesLayout = isTeam1 ? movesLayoutTeam1 : movesLayoutTeam2;
        movesLayout.setVisible(false);
        Label timerLabel = isTeam1 ? timerLabelTeam1 : timerLabelTeam2;
        timerLabel.setVisible(false);
    }
    public void showButtons() {
        movesLayoutTeam1.setVisible(true);
        movesLayoutTeam2.setVisible(true);
        timerLabelTeam1.setVisible(true);
        timerLabelTeam2.setVisible(true);
    }

    public Scene createScene() {
        return new Scene(this);
    }
}
