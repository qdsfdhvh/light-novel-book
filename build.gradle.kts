// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application").version("7.0.4").apply(false)
    id("com.android.library").version("7.0.4").apply(false)
    id("org.jetbrains.kotlin.android").version("1.6.20").apply(false)
    id("com.diffplug.spotless").version("6.5.0")
}

allprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt", "bin/**/*.kt", "buildSrc/**/*.kt")
            ktlint("0.45.2")
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint("0.45.2")
        }
        java {
            target("**/*.java")
            targetExclude("$buildDir/**/*.java", "bin/**/*.java")
        }
    }
}
