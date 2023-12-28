package org.example.Pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

        for (JsonElement typeElement : types) {
            String typeName = typeElement.getAsJsonObject().getAsJsonObject("type").get("name").getAsString();
            typing.add(PokeTyping.valueOf(typeName.toUpperCase()));
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
            // more data for moves later on !! NEED TO DO

            PokeTyping moveType = PokeTyping.NORMAL; // Standard Type
            int movePower = 50; // Standard Power
            int moveAccuracy = 100; // Standard accuracy

            attacks.add(new Moves(moveName, moveType, movePower, moveAccuracy));
        }

        return attacks;
    }
}
