plugins {
    id("de.jjohannes.extra-java-module-info") version "0.9"
    id("org.openjfx.javafxplugin") version "0.0.10"
}

javafx {
    version = "17"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
    )
}

val voskVersion = "0.3.33"

extraJavaModuleInfo {
    failOnMissingModuleInfo.set(false)
    module("vosk-${voskVersion}.jar", "org.vosk", voskVersion) {
        requires("com.sun.jna")
        exports("org.vosk")
    }
}

dependencies {
    implementation(project(":bloom-plugins:interface"))
    implementation(project(":bloom-plugins:interface-fx"))

    implementation("net.java.dev.jna:jna:5.9.0")
    implementation("com.alphacephei:vosk:${voskVersion}")

    implementation("org.reactivestreams:reactive-streams:1.0.3")
    implementation("io.projectreactor:reactor-core:3.4.12")
    implementation("org.pdfsam.rxjava3:rxjavafx:3.0.2")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
}
