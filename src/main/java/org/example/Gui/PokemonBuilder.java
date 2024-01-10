package org.example.Gui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Pokemon.Pokemon;



public class PokemonBuilder {

    private VBox pokemonBuilderLayout = new VBox(10);
    private Pokemon pokemon;

    public PokemonBuilder(Pokemon pokemon) {
        this.pokemon = pokemon;
        initializeLayout();
    }

    private void initializeLayout() {

        GridPane grid = new GridPane();

        ImageView pokemonImage = new ImageView(pokemon.getSpritePath());
        grid.add(pokemonImage, 1, 0);

        Label pokemonText = new Label("Pokémon");
        Label pokemonName = new Label(pokemon.getName());
        grid.add(pokemonText, 2, 0);
        grid.add(pokemonName, 3, 0);

        Label details = new Label("Details");
        Label level = new Label("Level: " + pokemon.getLevel());
        Label gender = new Label("Gender" + pokemon.getGender());

        grid.add(details, 0 ,1);
        grid.add(level, 1,1);
        grid.add(gender, 1 ,2);

        Label type = new Label();
        type.setText(pokemon.getTypeString());
        grid.add(type, 1,3);

        Label ability = new Label();
        // choose ability

        // moves

        //evs

        pokemonBuilderLayout.getChildren().add(grid);
    }

    public VBox getView() {
        return pokemonBuilderLayout;
    }
}
