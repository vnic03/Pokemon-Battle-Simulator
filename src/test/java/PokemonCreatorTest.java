import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;
import FailedIdeas.PokemonCreator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PokemonCreatorTest {
    @Test
    void testCreatePokemon() {

        String pikachuJson = "{"
                + "\"name\": \"Pikachu\","
                + "\"typing\": [{\"type\": \"ELECTRIC\"}],"
                + "\"stats\": {\"hp\": 35, \"attack\": 55, \"defense\": 40, \"speed\": 90},"
                + "\"attacks\": [\"thunder-shock\", \"quick-attack\"]"
                + "}";


        Pokemon pikachu = PokemonCreator.createPokemon(pikachuJson);


        assertEquals("Pikachu", pikachu.getName());
        if (!pikachu.getTyping().isEmpty()) {
            assertEquals(PokeTyping.ELECTRIC, pikachu.getTyping().get(0));
        }
    }
}
