import org.example.api.MoveApiClient
import org.example.pokemon.MoveCategory

def writeMove(String path, String moveName, MoveApiClient.MoveData data) {

    String typing = "Typing.${data.typing().name()}"
    String category  = "MoveCategory.${data.category().name()}"

    String entry = data.category() == MoveCategory.STATUS ?
            "// move(${format(moveName)}, $typing, $category, ${data.accuracy()}, ${data.pp()}, );"
            :
            "// move(${format(moveName)}, $typing, $category, ${data.power()}, ${data.accuracy()}, ${data.pp()}, );"

    println "Generated Move: $entry"

    File java = new File(path)
    String content = java.text

    String marker = "// $moveName"
    if (content.contains(marker)) {
        content = content.replace(marker, entry)
    }

    java.text = content
}

static def format(String name) {
    name.split('-').collect {
        it.capitalize()
    }.join(' ')
}

if (!binding.hasVariable('path') ||
        !binding.hasVariable('moveName') ||
        !binding.hasVariable('data'))
{
    println "Bindings missing!"
} else {
    writeMove(binding.variables.path, binding.variables.moveName, binding.variables.data)
}