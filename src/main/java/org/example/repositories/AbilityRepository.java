package org.example.repositories;

import org.example.pokemon.Ability;
import org.example.pokemon.Pokemon;
import org.example.pokemon.abilityEffects.*;

import java.util.HashMap;
import java.util.Map;

public class AbilityRepository {

    private final Map<String, Ability> abilities;

    public AbilityRepository() {
        abilities = new HashMap<>();
        initializeAbilities();
    }

    AbilityEffect starterEffect = new StarterEffect();

    private void initializeAbilities() {

        ability("Blaze", "Powers up Fire-type moves when the Pokémon's HP is low.", starterEffect);
        ability("Torrent", "Powers up Water-type moves when the Pokémon's HP is low.", starterEffect);
        ability("Overgrow", "Powers up Grass-type moves when the Pokémon's HP is low.", starterEffect);

        ability("Chlorophyll", "Boosts the Pokémon's Speed stat in sunshine.", new ChlorophyllEffect());
        ability("Solar Power", "Boosts the Sp. Atk stat in sunny weather, but HP decreases every turn.", new SolarPowerEffect());
        //abilities.put("Rain Dish", new Ability("Rain Dish", "The Pokémon gradually regains HP in rain."));


        //abilities.put("Levitate", new Ability("Levitate", "By floating in the air, the Pokémon receives full immunity to all Ground-type moves."));
        ability("Cursed Body", "May disable a move used on the Pokémon.", new CursedBodyEffect());

        ability("Static", "The Pokémon is charged with static electricity, so contact with it may cause paralysis.", new StaticEffect());
        ability("Flame Body", "Contact with the Pokémon may burn the foe.", new FlameBodyEffect());
        //abilities.put("Rock Head", new Ability("Rock Head", "Protects the Pokémon from recoil damage.", ));

        ability("Thick Fat", "Raises resistance to Fire-type and Ice-type moves.", new ThickFatEffect());

        ability("Battle Armor", "The Pokémon is protected against critical hits.", new PreventCritsEffect());

        ability("Guts", "Boosts Attack if there is a status problem.", new GutsEffect());

        ability("No Guard", "Ensures attacks by or against the Pokémon land.", new NoGuardEffect());

        ability("Swarm", "Powers up Bug-type moves when the Pokémon is in trouble.", starterEffect);

        ability("Technician", "Powers up the Pokémon's weaker moves.", new TechnicianEffect());

        ability("Keen Eye", "Prevents loss of accuracy.", new KeenEyeEffect());

        ability("Synchro", "Passes on a burn, poison, or paralysis to the foe.", new SynchroEffect());

        ability("Soundproof", "Gives full immunity to all sound-based moves.", new SoundProofEffect());

        ability("Filter", "Powers down super-effective moves.", new FilterEffect());

        ability("Scrappy", "Enables moves to hit Ghost-type foes.", new ScrappyEffect());

        ability("Air Lock", "Negates weather effects.", new AirLockEffect());

        ability("Sand Stream", "Summons a sandstorm.", new WeatherEffect());

        ability("Moxie", "Boosts Attack after knocking out any Pokémon.", new MoxieEffect());

        ability("Intimidate", "Lowers the foe's Attack stat.", new IntimidateEffect());

        ability("Clear Body", "Prevents the Pokémon's stats from being lowered.", new ClearBodyEffect());

        ability("Hydration", "Heals status problems if it is raining.", new HydrationEffect());

        ability("Shell Armor", "Blocks critical hits.", new PreventCritsEffect());

        ability("Water Absorb", "Changes water into HP.", new WaterAbsorbEffect());

        ability("Inner Focus", "Prevents flinching.", new InnerFocusEffect());

        ability("Multiscale", "Reduces damage when HP is full.", new MultiScaleEffect());

        ability("Marvel Scale", "Boosts Defense if there is a status problem", new MarvelScaleEffect());

        ability("Competitive", "Boosts the Sp. Atk stat when a stat is lowered.", new CompetitiveEffect());
    }

    public Ability getAbility(String name) {
        return abilities.get(name);
    }

    private void ability(String name, String description, AbilityEffect effect) {
        abilities.put(name, new Ability(name, description, effect));
    }

    public void assignAbilityToPokemon(Pokemon pokemon, String abilityName) {
        Ability ability = getAbility(abilityName);
        if (ability != null) {
            pokemon.setActiveAbility(ability);
        }
    }
}
