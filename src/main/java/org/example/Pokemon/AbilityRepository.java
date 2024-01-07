package org.example.Pokemon;

import org.example.Pokemon.AbilityEffects.*;

import java.util.HashMap;
import java.util.Map;

public class AbilityRepository {

    private final Map<String, Ability> abilities;

    public AbilityRepository() {
        abilities = new HashMap<>();
        initializeAbilities();
    }

    AbilityEffect starterEffect = new StarterEffect();
    AbilityEffect chlorophyllEffect = new ChlorophyllEffect();
    AbilityEffect solarPowerEffect = new SolarPowerEffect();
    AbilityEffect cursedBodyEffect = new CursedBodyEffect();
    AbilityEffect staticEffect = new StaticEffect();
    AbilityEffect battleArmorEffect = new BattleArmorEffect();
    AbilityEffect thickFatEffect = new ThickFatEffect();

    private void initializeAbilities() {

        abilities.put("Blaze", new Ability("Blaze", "Powers up Fire-type moves when the Pokémon's HP is low.", starterEffect));
        abilities.put("Torrent", new Ability("Torrent", "Powers up Water-type moves when the Pokémon's HP is low.", starterEffect));
        abilities.put("Overgrow", new Ability("Overgrow", "Powers up Grass-type moves when the Pokémon's HP is low.", starterEffect));

        abilities.put("Chlorophyll", new Ability("Chlorophyll", "Boosts the Pokémon's Speed stat in sunshine.", chlorophyllEffect));
        abilities.put("Solar Power", new Ability("Solar Power", "Boosts the Sp. Atk stat in sunny weather, but HP decreases every turn.", solarPowerEffect));
        //abilities.put("Rain Dish", new Ability("Rain Dish", "The Pokémon gradually regains HP in rain."));

        //abilities.put("Intimidate", new Ability("Intimidate", "The Pokémon intimidates opposing Pokémon upon entering battle, lowering their Attack stat."));
        //abilities.put("Levitate", new Ability("Levitate", "By floating in the air, the Pokémon receives full immunity to all Ground-type moves."));
        abilities.put("Cursed Body", new Ability("Cursed Body", "May disable a move used on the Pokémon.", cursedBodyEffect));

        abilities.put("Static", new Ability("Static", "The Pokémon is charged with static electricity, so contact with it may cause paralysis.", staticEffect));
        //abilities.put("Rock Head", new Ability("Rock Head", "Protects the Pokémon from recoil damage.", ));
        abilities.put("Thick Fat", new Ability("Thick Fat", "Raises resistance to Fire-type and Ice-type moves.", thickFatEffect));
        abilities.put("Battle Armor", new Ability("Battle Armor","The Pokémon is protected against critical hits.", battleArmorEffect));
    }

    public Ability getAbility(String name) {
        return abilities.get(name);
    }

    public void assignAbilityToPokemon(Pokemon pokemon, String abilityName) {
        Ability ability = getAbility(abilityName);
        if (ability != null) {
            pokemon.setActiveAbility(ability);
        }
    }
}
