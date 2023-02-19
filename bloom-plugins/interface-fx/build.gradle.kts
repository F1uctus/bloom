plugins {
    id("org.openjfx.javafxplugin") version "0.0.10"
}

javafx {
    version = "19"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
    )
}

dependencies {
    implementation(project(":bloom-plugins:interface"))

    implementation(libs.reactive.streams)
    implementation(libs.reactor.core)
    implementation(libs.rxjavafx)
    implementation(libs.fxform2)
}
