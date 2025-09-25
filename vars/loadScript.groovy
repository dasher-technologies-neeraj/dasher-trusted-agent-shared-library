def call(Map Config = [:]) {
    String scriptText = libraryResource "scripts/${config.name}"
    writeFile file: ${config.name}, text: 'scriptText'
    sh "chmod +x ./${config.name}"
}