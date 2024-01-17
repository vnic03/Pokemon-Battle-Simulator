package org.example.Gui.battleScene;

import javafx.geometry.Pos;
import javafx.geometry.Side;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BattleView extends AnchorPane {
    private Team team1;
    private Team team2;
    private HBox team1Container = new HBox(10);
    private HBox team2Container = new HBox(10);
    private VBox playerOneView = new VBox(10);
    private VBox playerTwoView = new VBox(10);


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

        playerOneView.getChildren().add(team1Container);
        playerTwoView.getChildren().add(team2Container);

        this.getChildren().addAll(playerOneView, playerTwoView);
    }

    public void loadTeams(Team team1, Team team2) {

        team1Container.getChildren().clear();
        team2Container.getChildren().clear();

        if (!team1.getPokemons().isEmpty()) {
            Pokemon pokemonTeam1 = team1.getPokemons().getFirst();
            if (pokemonTeam1 != null) {
                ImageView spriteView = new ImageView(pokemonTeam1.getBackSprite());
                spriteView.setFitWidth(200);
                spriteView.setFitHeight(200);
                spriteView.setPreserveRatio(true);
                spriteView.setSmooth(true);
                PokemonStatusBar statusBar = new PokemonStatusBar(pokemonTeam1);
                team1Container.getChildren().addAll(spriteView, statusBar);

                List<Button> moveButtonsTeam1 = createMovesButtonForTeam(pokemonTeam1);
                Button switchButtonTeam1 = createSwitchButton(team1, true);
                VBox movesLayoutTeam1 = createMovesLayout(moveButtonsTeam1, switchButtonTeam1);
                HBox fullLayoutTeam1 = new HBox(movesLayoutTeam1, team1Container);
                playerOneView.getChildren().add(fullLayoutTeam1);
                AnchorPane.setBottomAnchor(playerOneView, 150.0);
                AnchorPane.setLeftAnchor(playerOneView, 200.0);
            }
        }

        if (!team2.getPokemons().isEmpty()) {
            Pokemon pokemonTeam2 = team2.getPokemons().getFirst();

            if (pokemonTeam2 != null) {
                ImageView spriteView = new ImageView(pokemonTeam2.getFrontSprite());
                spriteView.setFitWidth(200);
                spriteView.setFitHeight(200);
                spriteView.setPreserveRatio(true);
                spriteView.setSmooth(true);

                PokemonStatusBar statusBar = new PokemonStatusBar(pokemonTeam2);
                List<Button> moveButtonsTeam2 = createMovesButtonForTeam(pokemonTeam2);
                Button switchButtonTeam2 = createSwitchButton(team2, false);
                VBox movesLayoutTeam2 = createMovesLayout(moveButtonsTeam2, switchButtonTeam2);

                HBox fullLayoutTeam2 = new HBox(statusBar, spriteView, movesLayoutTeam2);
                playerTwoView.getChildren().add(fullLayoutTeam2);
                AnchorPane.setTopAnchor(playerTwoView, 150.0);
                AnchorPane.setRightAnchor(playerTwoView, 300.0);

                team2Container.getChildren().addAll(fullLayoutTeam2);

            }
        }
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
    }

    public void handlePokemonChangeRequest(Team team, int pokemonIndex, boolean isTeam1) {
        if (canPokemonSwitch(team.getPokemons().get(pokemonIndex))) {
            changePokemon(team, pokemonIndex, isTeam1);
            team.setActivePokemonIndex(pokemonIndex);
            updateTeamView(isTeam1 ? team1Container : team2Container, team, isTeam1);

            List<Button> movesButtons = createMovesButtonForTeam(team.getPokemons().get(pokemonIndex));
            Button switchButton = createSwitchButton(team, isTeam1);
            VBox movesLayout = createMovesLayout(movesButtons, switchButton);

            VBox view = isTeam1 ? playerOneView : playerTwoView;
            view.getChildren().removeIf(node -> node instanceof VBox && node.getStyleClass().contains("moves-layout"));
            view.getChildren().add(movesLayout);
            view.getStyleClass().add("moves-layout");

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
       Button switchButton = new Button("Switch Pokemon");
       switchButton.getStyleClass().add("switch-button");
       switchButton.setOnAction(event -> {
           ContextMenu switchMenu = new ContextMenu();

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
                //moveButton.setOnAction(event -> handleMoveSelection(move, activePokemon, isTeam1));
                moveButtons.add(moveButton);
            }
        }
        return moveButtons;
    }
    private void handleMoveSelection(Moves move, Pokemon activePokemon, boolean isTeam1) {
        // for later: what happens when a move gets chosen
    }
    private VBox createMovesLayout(List<Button> moveButtons, Button switchButton) {
        HBox topRow = new HBox(10);
        HBox bottomRow = new HBox(10);

        for (int i = 0; i < moveButtons.size(); i++) {
            if (i < 2) {
                topRow.getChildren().add(moveButtons.get(i));
            } else {
                bottomRow.getChildren().add(moveButtons.get(i));
            }
        }
        topRow.setAlignment(Pos.CENTER);
        bottomRow.setAlignment(Pos.CENTER);

        VBox movesLayout = new VBox(20);
        movesLayout.getChildren().add(switchButton);
        if (!topRow.getChildren().isEmpty()) {
            movesLayout.getChildren().add(topRow);
        }
        if (!bottomRow.getChildren().isEmpty()) {
            movesLayout.getChildren().add(bottomRow);
        }
        movesLayout.setAlignment(Pos.CENTER);
        return movesLayout;
    }
    public Scene createScene() {
        return new Scene(this);
    }
}
