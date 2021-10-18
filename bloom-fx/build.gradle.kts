plugins {
    application
    id("org.springframework.boot")
    id("org.openjfx.javafxplugin") version "0.0.10"
}

application {
    mainClass.set("com.f1uctus.bloom.application.JavaFxWeaverApplication")
}

tasks.bootJar {
    mainClass.set(application.mainClass)
    archiveAppendix.set("boot")
}

javafx {
    version = "17"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
    )
}

dependencies {
    implementation(project(":bloom-core"))
    implementation(project(":bloom-interfaces"))
    implementation(project(":bloom-interfaces-speech"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("net.rgielen:javafx-weaver-spring-boot-starter:1.3.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    implementation("org.reactivestreams:reactive-streams:1.0.3")
    implementation("org.pdfsam.rxjava3:rxjavafx:3.0.2")

    implementation("com.fasterxml.jackson.core:jackson-databind")
}
