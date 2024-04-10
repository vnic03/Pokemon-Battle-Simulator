
static def update(List<String> abilities, String path) {

    def javaFile = new File(path)
    def javaContent = javaFile.text

    def existingEnumPattern = ~/(?s)public enum Name \{(.*?)};/
    def matcher = existingEnumPattern.matcher(javaContent)

    def existingEnums = []
    if (matcher.find()) {
        String enumBlock = matcher.group(1).trim()
        existingEnums = enumBlock.split(',\n').collect { it.trim() }
    }

    abilities.each {
        String newEnum = it.toUpperCase().replace('-', '_')
        if (!existingEnums.contains(newEnum + ",")) {
            existingEnums.add(newEnum)
        }
    }

    def enumContent = existingEnums.sort().join(",\n")
    enumContent = "public enum Name {\n    ${enumContent}\n};"

    javaFile.write(javaContent.replaceAll(existingEnumPattern, enumContent))
}

if (!binding.hasVariable("abilities") || !binding.hasVariable("path")) {
    println "Bindings missing!"
} else {
    update(binding.variables.abilities, binding.variables.path)
}

