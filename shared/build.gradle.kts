plugins {
    kotlin("multiplatform")

    // Kotlin serialization consists of a compiler plugin, that generates visitor code for serializable classes
    kotlin("plugin.serialization") version "1.6.10"

    id("com.android.library")
}


// Sources for dependencies
repositories {
    mavenCentral()
    mavenLocal()
    google()
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("io.ktor:ktor-client-core:2.0.0")
                api("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
                api("io.ktor:ktor-client-content-negotiation:2.0.0")
                api("io.ktor:ktor-client-logging:2.0.0")

                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
                api("io.github.aakira:napier:2.1.0")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("org.slf4j:slf4j-simple:1.7.30")
                api("io.ktor:ktor-client-okhttp:2.0.0")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.0.0")
            }

        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.dragos.ageguessing"
    compileSdk = 32
    defaultConfig {
        minSdk = 23
        targetSdk = 32
    }
}