import org.example.Pokemon.*;
import org.example.Pokemon.Effects.MayFlinchEffect;
import org.example.Pokemon.Effects.MoveEffect;

import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class FlinchTest {

    @Test
    void testFlinchEffect() {
        MoveEffect flinch = new TestableMayFlinchEffect();

        Pokemon one = new Pokemon(
                "Pikachu",
                Collections.singletonList(PokeTyping.ELECTRIC),
                new Stats(35, 55, 40, 50, 50, 90),
                Nature.TIMID,
                Arrays.asList(
                        new Moves("Thunderbolt", PokeTyping.ELECTRIC, MoveCategory.SPECIAL, 90, 100, 15, flinch),
                        new Moves("Quick Attack", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 40, 100, 30, flinch),
                        new Moves("Iron Tail", PokeTyping.STEEL, MoveCategory.PHYSICAL, 100, 75, 15, flinch),
                        new Moves("Electro Ball", PokeTyping.ELECTRIC, MoveCategory.SPECIAL, 0, 100, 10, flinch)
                )
        );


        Pokemon two = new Pokemon(
                "Charizard",
                Arrays.asList(PokeTyping.FIRE, PokeTyping.FLYING),
                new Stats(78, 84, 78, 109, 85, 100),
                Nature.MODEST,
                Arrays.asList(
                        new Moves("Flamethrower", PokeTyping.FIRE, MoveCategory.SPECIAL, 90, 100, 15, flinch),
                        new Moves("Air Slash", PokeTyping.FLYING, MoveCategory.SPECIAL, 75, 95, 15, flinch),
                        new Moves("Dragon Claw", PokeTyping.DRAGON, MoveCategory.PHYSICAL, 80, 100, 15, flinch),
                        new Moves("Solar Beam", PokeTyping.GRASS, MoveCategory.SPECIAL, 120, 100, 10, flinch)
                )
        );




        flinch.apply(one, two);

        assertTrue(two.isFlinching(), "Defender sollte geflincht sein");
    }

    private static class TestableMayFlinchEffect extends MayFlinchEffect {


        public TestableMayFlinchEffect() {
            super(0.3);
        }

        @Override
        public void apply(Pokemon user, Pokemon target) {
            target.setFlinching(true);
        }
    }
}
