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
    version = "17"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
    )
}

dependencies {
    implementation(project(":bloom-core"))
    implementation(project(":bloom-plugins:interface"))
    implementation(project(":bloom-plugins:interface-fx"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("net.rgielen:javafx-weaver-spring-boot-starter:1.3.0")
    implementation("org.reactivestreams:reactive-streams:1.0.3")
    implementation("org.pdfsam.rxjava3:rxjavafx:3.0.2")
    implementation("com.dooapp.fxform2:core:9.0.0")

    implementation("com.fasterxml.jackson.core:jackson-databind")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")
    testImplementation("org.testfx:openjfx-monocle:jdk-12.0.1+2")
}
