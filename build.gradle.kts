import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    `java-library`
    id("org.springframework.boot") version "2.6.0" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.freefair.lombok") version "6.3.0"
}

val pf4jVersion: String by project
val pluginsDir by extra { file("$buildDir/plugins") }

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/milestone")
    maven(url = "https://repo.spring.io/snapshot")
}

tasks.named("build") {
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

    lombok {
        version.set("1.18.22")
    }

    ext["junit-jupiter.version"] = "5.8.2"

    dependencyManagement {
        imports {
            mavenBom(SpringBootPlugin.BOM_COORDINATES)
        }
    }

    dependencies {
        implementation("com.google.guava:guava:31.0.1-jre")
        implementation("com.oath.cyclops:cyclops:10.4.0")

        implementation("org.pf4j:pf4j:${pf4jVersion}") {
            exclude(group = "org.slf4j")
        }

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
