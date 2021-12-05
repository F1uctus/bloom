pluginManagement {
    repositories {
        maven(url = "https://repo.spring.io/milestone")
        maven(url = "https://repo.spring.io/snapshot")
        gradlePluginPortal()
    }
}

rootProject.name = "bloom"

include("bloom-plugins")
include("bloom-plugins:interface")
include("bloom-plugins:interface-fx")
include("bloom-plugins:speech")

include("bloom-core")
include("bloom-fx")
