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

    implementation("org.reactivestreams:reactive-streams:1.0.4")
    implementation("io.projectreactor:reactor-core:3.5.3")
    implementation("org.pdfsam.rxjava3:rxjavafx:3.0.2")
    implementation("com.dooapp.fxform2:core:9.0.0")
}
