pluginManagement {
    repositories {
        maven(url = "https://repo.spring.io/milestone")
        maven(url = "https://repo.spring.io/snapshot")
        gradlePluginPortal()
    }
}
rootProject.name = "bloom"
include("bloom-interfaces")
include("bloom-interfaces-speech")
include("bloom-core")
include("bloom-fx")
