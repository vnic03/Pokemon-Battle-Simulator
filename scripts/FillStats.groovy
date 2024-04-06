
def writeStats(String path, Map stats) {
    println "Given stats: $stats"
    String statsString = format(stats)

    println "Generated Stats-String: $statsString"

    File java = new File(path)
    String content = java.text
    String update = content.replaceAll(/\/\/ stats\(\);/, statsString)

    java.text = update
}

static def format(Map stats) {
    List<String> statsOrder = ['hp', 'attack', 'defense', 'special-attack', 'special-defense', 'speed']
    List<String> statsValues = statsOrder.collect { key -> stats[key]?.toString() ?: '0' }

    return "stats(${statsValues.join(', ')})"
}

if (!binding.hasVariable('path') ||
        !binding.hasVariable('pokemonName') ||
        !binding.hasVariable('stats'))
{
    println "Bindings missing: 'path', 'pokemonName', 'stats'"
} else {
    writeStats(binding.variables.path, binding.variables.stats)
}

