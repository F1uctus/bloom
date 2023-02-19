import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    `java-library`
    id("org.springframework.boot") version "3.0.2" apply false
    id("io.spring.dependency-management") version "1.1.0"
    id("io.freefair.lombok") version "6.6.2"
    id("org.javamodularity.moduleplugin") version "1.8.12"
}

val pf4jVersion: String by project
val pluginsDir by extra { file("$buildDir/plugins") }

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/milestone")
    maven(url = "https://repo.spring.io/snapshot")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.named("build") {
    dependsOn(":bloom-plugins:assemblePlugins")
    dependsOn(":bloom-fx:bootJar")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "io.freefair.lombok")

    group = "com.f1uctus.bloom"
    version = "0.0.1-SNAPSHOT"

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        mavenCentral()
        maven(url = "https://alphacephei.com/maven/")
        maven(url = "https://repo.spring.io/milestone")
        maven(url = "https://repo.spring.io/snapshot")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencyManagement {
        imports {
            mavenBom(SpringBootPlugin.BOM_COORDINATES)
        }
    }

    dependencies {
        implementation("com.google.guava:guava:31.1-jre")
        implementation("com.oath.cyclops:cyclops:10.4.1")

        implementation("org.pf4j:pf4j:$pf4jVersion")

        implementation("net.java.dev.jna:jna:5.13.0")
        implementation("com.alphacephei:vosk:0.3.45")

        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("org.junit.jupiter:junit-jupiter-params")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

        annotationProcessor("org.projectlombok:lombok")
    }

    configurations.all {
        exclude(group = "com.google.code.findbugs")
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
        test {
            useJUnitPlatform()
        }
    }
}
