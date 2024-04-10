
def writePokemon(String path, String pokemonName, int dex,
                 String typing, Map stats, List<String> abilities)
{
    String abilitiesEntry = abilities.collect { ability ->
        "Ability.Name.${ability.toUpperCase().replaceAll('-', '_')}"
    }.join(", ")

    String entry = abilitiesEntry.isEmpty() ?
            "// pokemon(\"${pokemonName.capitalize()}\", ${dex}, ${typing}, ${format(stats)}, );"
            :
            "pokemon(\"${pokemonName.capitalize()}\", ${dex}, ${typing}, ${format(stats)}, ${abilitiesEntry});"

    println "Generated Pokémon: $entry"

    File java = new File(path)
    String content = java.text

    String marker = "// ${pokemonName}"
    if (content.contains(marker)) {
        content = content.replace(marker, entry)
    }

    java.text = content
}

static def format(Map stats) {
    List<String> statsOrder = ['hp', 'attack', 'defense', 'special-attack', 'special-defense', 'speed']
    List<String> statsValues = statsOrder.collect { key -> stats[key]?.toString() ?: '0' }

    return "stats(${statsValues.join(', ')})"
}

if (!binding.hasVariable('path') ||
        !binding.hasVariable('pokemonName') ||
        !binding.hasVariable('dex') ||
        !binding.hasVariable('typing') ||
        !binding.hasVariable('stats') ||
        !binding.hasVariable('abilities'))
{
    println "Bindings missing!"
} else {
    writePokemon(binding.variables.path, binding.variables.pokemonName, binding.variables.dex,
                    binding.variables.typing, binding.variables.stats, binding.variables.abilities)
}
