plugins {
    application
    id("org.springframework.boot")
    id("org.openjfx.javafxplugin") version "0.0.10"
}

val mainAppClass = "com.f1uctus.bloom.application.JavaFxWeaverApplication"
val pluginsDir: File by rootProject.extra

application {
    mainClass.set(mainAppClass)
}

tasks.bootJar {
    mainClass.set(mainAppClass)
    archiveAppendix.set("boot")
}

tasks.bootRun {
    systemProperty("pf4j.pluginsDir", pluginsDir.absolutePath)
}

javafx {
    version = "19"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
    )
}

dependencies {
    implementation(project(":bloom-core"))
    implementation(project(":bloom-plugins:interface"))
    implementation(project(":bloom-plugins:interface-fx"))

    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)

    implementation(libs.spring.boot.starter.javafx.weaver)
    implementation(libs.reactive.streams)
    implementation(libs.rxjavafx)
    implementation(libs.fxform2)

    implementation(libs.jackson.databind)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.testfx.junit5)
    testImplementation(libs.testfx.monocle)
}
