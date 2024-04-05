import groovy.json.JsonSlurper

static def update(json, java) {
    def jsonFile = new File(json)
    def abilities = new JsonSlurper().parseText(jsonFile.text)

    def enumContent = abilities.keySet().collect { "    ${it.toUpperCase().replace('-', '_')}," }.join("\n")
    enumContent = "public enum Name {\n${enumContent}\n};"

    def javaFile = new File(java)
    def javaContent = javaFile.text

    def pattern = ~/(?s)public enum Name \{.*?};/
    def newJavaContent = javaContent.replaceAll(pattern, enumContent)

    javaFile.write(newJavaContent)
}

if (this.args.length > 0) {
    update('src/main/java/org/example/pokemon/ability/abilities.json',
            'src/main/java/org/example/pokemon/Ability.java')
} else {
    println "Usage: groovy <scriptName> <jsonPath> <javaFilePath>"
}
