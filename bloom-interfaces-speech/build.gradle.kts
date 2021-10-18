plugins {
    id("de.jjohannes.extra-java-module-info") version "0.9"
}

extraJavaModuleInfo {
    failOnMissingModuleInfo.set(false)
    module("vosk-0.3.31.jar", "org.vosk", "0.3.31") {
        requires("com.sun.jna")
        exports("org.vosk")
    }
}

dependencies {
    implementation(project(":bloom-interfaces"))

    implementation("net.java.dev.jna:jna:5.9.0")
    implementation("com.alphacephei:vosk:0.3.31")

    implementation("io.projectreactor:reactor-core:3.4.10")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
}
