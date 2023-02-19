pluginManagement {
    repositories {
        maven(url = "https://repo.spring.io/milestone")
        maven(url = "https://repo.spring.io/snapshot")
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("pf4j", "org.pf4j:pf4j:3.8.0")
            library("spring-boot-configuration-processor", "org.springframework.boot", "spring-boot-configuration-processor").withoutVersion()
            library("spring-boot-starter", "org.springframework.boot", "spring-boot-starter").withoutVersion()
            library("spring-boot-starter-validation", "org.springframework.boot", "spring-boot-starter-validation").withoutVersion()
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").withoutVersion()
            library("spring-boot-starter-data-jpa", "org.springframework.boot", "spring-boot-starter-data-jpa").withoutVersion()
            library("spring-boot-starter-javafx-weaver", "net.rgielen:javafx-weaver-spring-boot-starter:1.3.0")
            library("jupiter-api", "org.junit.jupiter", "junit-jupiter-api").withoutVersion()
            library("jupiter-params", "org.junit.jupiter", "junit-jupiter-params").withoutVersion()
            library("jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").withoutVersion()
            library("lombok", "org.projectlombok", "lombok").withoutVersion()
            library("jackson-databind", "com.fasterxml.jackson.core", "jackson-databind").withoutVersion()
            library("h2", "com.h2database", "h2").withoutVersion()
            library("h2-r2dbc", "io.r2dbc", "r2dbc-h2").withoutVersion()
            library("guava", "com.google.guava:guava:31.1-jre")
            library("cyclops", "com.oath.cyclops:cyclops:10.4.1")
            library("vosk", "com.alphacephei:vosk:0.3.45")
            library("jna", "net.java.dev.jna:jna:5.13.0")
            library("reactive-streams", "org.reactivestreams:reactive-streams:1.0.4")
            library("reactor-core", "io.projectreactor:reactor-core:3.5.3")
            library("rxjavafx", "org.pdfsam.rxjava3:rxjavafx:3.0.2")
            library("fxform2", "com.dooapp.fxform2:core:9.0.0")
            library("testfx-junit5", "org.testfx:testfx-junit5:4.0.16-alpha")
            library("testfx-monocle", "org.testfx:openjfx-monocle:jdk-12.0.1+2")
        }
    }
}

rootProject.name = "bloom"

include("bloom-plugins")
include("bloom-plugins:interface")
include("bloom-plugins:interface-fx")
include("bloom-plugins:speech")
include("bloom-plugins:commandline")

include("bloom-core")
include("bloom-fx")
