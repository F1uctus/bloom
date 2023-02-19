plugins {
    kotlin("jvm") version "1.8.10"
}

val pluginsDir by extra { file("$buildDir/plugins") }

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.freefair.lombok")

    group = "com.f1uctus.bloom"
    version = "0.0.1-SNAPSHOT"

    if (name.contains("interface")) {
        return@subprojects
    }

    val pluginId: String by project
    val pluginClass: String by project
    val pluginProvider: String by project

    val project = this

    tasks.register<Jar>("plugin") {
        archiveBaseName.set("plugin-${pluginId}")

        into("classes") {
            with(tasks.named<Jar>("jar").get())
        }

        dependsOn(tasks.named<Jar>("jar"))
        into("lib") {
            from({
                configurations["compileClasspath"].filter { it.name.endsWith("jar") }
            })
        }
        archiveExtension.set("zip")
    }

    tasks.register<Copy>("assemblePlugin") {
        from(project.tasks.named("plugin"))
        into(pluginsDir)
    }

    tasks.named<Jar>("jar") {
        manifest {
            attributes["Plugin-Class"] = pluginClass
            attributes["Plugin-Id"] = pluginId
            attributes["Plugin-Version"] = archiveVersion
            attributes["Plugin-Provider"] = pluginProvider
        }
    }

    tasks.named("build") {
        dependsOn(tasks.named("plugin"))
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
        test {
            useJUnitPlatform()
        }
    }

    dependencies {
        annotationProcessor(rootProject.libs.pf4j)
    }
}

tasks.register<Copy>("assemblePlugins") {
    dependsOn(subprojects
        .filter { !it.name.contains("interface") }
        .map { it.tasks.named("assemblePlugin") }
    )
}

tasks {
    "build" {
        dependsOn(named("assemblePlugins"))
    }
}
