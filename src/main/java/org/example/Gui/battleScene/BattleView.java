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
import org.example.Pokemon.Pokemon;
import org.example.teams.Team;
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

        Button switchButtonTeam1 = new Button("Switch Pokemon");
        setUpSwitchButton(switchButtonTeam1, this.team1, true);
        playerOneView.getChildren().add(switchButtonTeam1);

        Button switchButtonTeam2 = new Button("Switch Pokemon");
        setUpSwitchButton(switchButtonTeam2, this.team2, false);
        playerTwoView.getChildren().add(switchButtonTeam2);
    }

    private void initializeBattleField() {

        team1Container.setAlignment(Pos.CENTER_LEFT);
        team2Container.setAlignment(Pos.CENTER_RIGHT);

        playerOneView.getChildren().add(team1Container);
        playerTwoView.getChildren().add(team2Container);


        AnchorPane.setBottomAnchor(playerOneView, 50.0);
        AnchorPane.setLeftAnchor(playerOneView, 50.0);

        AnchorPane.setTopAnchor(playerTwoView, 50.0);
        AnchorPane.setRightAnchor(playerTwoView, 50.0);

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
                team2Container.getChildren().addAll(spriteView, statusBar);
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

            if (isTeam1) {
                updateTeamView(team1Container, team, true);
            } else {
                updateTeamView(team2Container, team, false);
            }
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

   private void setUpSwitchButton(Button switchButton, Team team, boolean isTeam1) {
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
   }
    private boolean canPokemonSwitch(Pokemon pokemon) {
        // add logic later
        return true;
    }
    public Scene createScene() {
        return new Scene(this);
    }
}
