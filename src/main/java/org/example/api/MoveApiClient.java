package org.example.api;

import org.example.Constants;
import org.example.pokemon.MoveCategory;
import org.example.pokemon.Typing;
import org.json.JSONObject;
import java.util.concurrent.CompletableFuture;

public class MoveApiClient extends ApiClient {

    static {
        setBaseUrl(MoveApiClient.class.getName(), Constants.POKE_API_MOVE_URL);
    }

    public static CompletableFuture<MoveData> fetchMove(String moveName) {
        return fetchData(moveName).thenApply(data -> {
            JSONObject obj = new JSONObject(data);

            Typing typing = Typing.valueOf(
                    obj.getJSONObject("type").getString("name").toUpperCase()
            );

            MoveCategory category = MoveCategory.valueOf(
                    obj.getJSONObject("damage_class").getString("name").toUpperCase()
            );

            int power = obj.optInt("power", 0);
            int accuracy = obj.getInt("accuracy");
            int pp = obj.getInt("pp");

            return new MoveData(typing, category, power, accuracy, pp);
        });
    }

    public record MoveData(
            Typing typing,
            MoveCategory category,
            int power,
            int accuracy,
            int pp) { }
}
