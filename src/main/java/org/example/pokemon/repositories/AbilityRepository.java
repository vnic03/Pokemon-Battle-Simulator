package org.example.pokemon.repositories;

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
    AbilityEffect chlorophyllEffect = new ChlorophyllEffect();
    AbilityEffect solarPowerEffect = new SolarPowerEffect();
    AbilityEffect cursedBodyEffect = new CursedBodyEffect();
    AbilityEffect staticEffect = new StaticEffect();
    AbilityEffect battleArmorEffect = new BattleArmorEffect();
    AbilityEffect thickFatEffect = new ThickFatEffect();
    AbilityEffect gutsEffect = new GutsEffect();
    AbilityEffect noGuardEffect = new NoGuardEffect();
    AbilityEffect technicianEffect = new TechnicianEffect();
    AbilityEffect flameBodyEffect = new FlameBodyEffect();
    AbilityEffect keenEyeEffect = new KeenEyeEffect();
    AbilityEffect synchroEffect = new SynchroEffect();
    AbilityEffect soundproofEffect = new SoundProofEffect();
    AbilityEffect filterEffect = new FilterEffect();
    AbilityEffect scrappyEffect = new ScrappyEffect();
    AbilityEffect airlockEffect = new AirLockEffect();
    AbilityEffect weatherEffect = new WeatherEffect();
    AbilityEffect intimidateEffect = new IntimidateEffect();
    AbilityEffect moxieEffect = new MoxieEffect();

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
        abilities.put("Flame Body", new Ability("Flame Body", "Contact with the Pokémon may burn the foe.", flameBodyEffect));

        //abilities.put("Rock Head", new Ability("Rock Head", "Protects the Pokémon from recoil damage.", ));

        abilities.put("Thick Fat", new Ability("Thick Fat", "Raises resistance to Fire-type and Ice-type moves.", thickFatEffect));

        abilities.put("Battle Armor", new Ability("Battle Armor","The Pokémon is protected against critical hits.", battleArmorEffect));

        abilities.put("Guts", new Ability("Guts", "Boosts Attack if there is a status problem.", gutsEffect));

        abilities.put("No Guard", new Ability("No Guard", "Ensures attacks by or against the Pokémon land.", noGuardEffect));

        abilities.put("Swarm", new Ability("Swarm", "Powers up Bug-type moves when the Pokémon is in trouble.", starterEffect));

        abilities.put("Technician", new Ability("Technician", "Powers up the Pokémon's weaker moves.", technicianEffect));

        abilities.put("Keen Eye", new Ability("Keen Eye", "Prevents loss of accuracy.", keenEyeEffect));

        abilities.put("Synchro", new Ability("Synchro", "Passes on a burn, poison, or paralysis to the foe.", synchroEffect));

        abilities.put("Soundproof", new Ability("Soundproof", "Gives full immunity to all sound-based moves.", soundproofEffect));

        abilities.put("Filter", new Ability("Filter", "Powers down super-effective moves", filterEffect));

        abilities.put("Scrappy", new Ability("Scrappy", "Enables moves to hit Ghost-type foes.", scrappyEffect));

        abilities.put("Air Lock", new Ability("Air Lock", "Negates weather effects.", airlockEffect));

        abilities.put("Sand Stream", new Ability("Sand Stream", "Summons a sandstorm.", weatherEffect));

        abilities.put("Moxie", new Ability("Moxie", "Boosts Attack after knocking out any Pokémon.", moxieEffect));

        abilities.put("Intimidate", new Ability("Intimidate", "Lowers the foe's Attack stat.", intimidateEffect));
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
