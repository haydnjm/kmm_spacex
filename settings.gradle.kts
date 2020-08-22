pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android" || requested.id.name == "kotlin-android-extensions") {
                useModule("com.android.tools.build:gradle:4.0.1")
            }
        }
    }
}
<<<<<<< HEAD
rootProject.name = "KMM_SpaceX"
=======
rootProject.name = "KMMApp"
>>>>>>> 0028c58... Create project structure


include(":androidApp")
include(":shared")

