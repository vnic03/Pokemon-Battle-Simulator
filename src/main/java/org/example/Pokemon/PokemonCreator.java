package org.example.Pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.Pokemon.Effects.MoveEffect;
import org.example.Pokemon.Effects.NoEffect;

import java.util.ArrayList;
import java.util.List;

public class PokemonCreator {

    public static Pokemon createPokemon(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        String name = jsonObject.get("name").getAsString();

        List<PokeTyping> typing = extractTyping(jsonObject);

        Stats stats = extractStats(jsonObject);

        List<Moves> attacks = extractMoves(jsonObject);

        return new Pokemon(name, typing, stats, attacks);
    }


    private static List<PokeTyping> extractTyping(JsonObject jsonObject) {

        JsonArray types = jsonObject.getAsJsonArray("types");

        List<PokeTyping> typing = new ArrayList<>();

        if (types != null) {
            for (JsonElement typeElement : types) {
                String typeName = typeElement.getAsJsonObject().getAsJsonObject("type").get("name").getAsString();
                typing.add(PokeTyping.valueOf(typeName.toUpperCase()));
            }
        }
        return typing;
    }

    private static Stats extractStats(JsonObject jsonObject) {

        JsonArray stats = jsonObject.getAsJsonArray("stats");

        int hp = 0, attack = 0, defense = 0, speed = 0;

        for (JsonElement statsElement : stats) {
            JsonObject stat = statsElement.getAsJsonObject().getAsJsonObject("stat");
            String statName = stat.get("name").getAsString();

            int baseStat = statsElement.getAsJsonObject().get("base_stat").getAsInt();

            switch (statName) {
                case "hp": hp = baseStat; break;
                case "attack": attack = baseStat; break;
                case "defense": defense = baseStat; break;
                case "speed": speed = baseStat; break;
            }
        }
        return new Stats(hp, attack, defense, speed);
    }

    private static List<Moves> extractMoves(JsonObject jsonObject) {

        JsonArray moves = jsonObject.getAsJsonArray("moves");

        List<Moves> attacks = new ArrayList<>();

        for (JsonElement moveElement : moves) {
            String moveName = moveElement.getAsJsonObject().getAsJsonObject("move").get("name").getAsString();
            String moveDetailsJson = MoveFetcher.fetchMoveDetails(moveName);

            if (moveDetailsJson != null) {
                JsonObject moveDetails = JsonParser.parseString(moveDetailsJson).getAsJsonObject();
                PokeTyping moveType = PokeTyping.valueOf(moveDetails.getAsJsonObject("type").get("name").getAsString().toUpperCase());
                MoveCategory cat = MoveCategory.valueOf(moveDetails.getAsJsonObject("damage_class").get("name").getAsString().toUpperCase());
                int movePower = moveDetails.get("power").getAsInt();
                int moveAccuracy = moveDetails.get("accuracy").getAsInt();
                int pp = moveDetails.get("pp").getAsInt();
                MoveEffect effect = new NoEffect(); // Change logic on how to use effect !!

                attacks.add(new Moves(moveName, moveType, cat, movePower, moveAccuracy, pp, effect));
            }
        }
        return attacks;
    }
}
