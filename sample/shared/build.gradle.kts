import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.dokar.chiptextfield.sample.shared"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
}

kotlin {
    jvmToolchain(11)

    jvm()

    js(IR) {
        browser()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        binaries.executable()
    }

    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()


    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":chiptextfield-core"))
                api(project(":chiptextfield"))
                api(project(":chiptextfield-m3"))
                api(compose.material)
                api(compose.material3)
            }
        }

        val nonWasmJsMain by creating {
            dependencies {
                implementation(libs.coil.compose)
                implementation(libs.coil.network)
            }
        }

        val jvmMain by getting {
            dependencies {
                dependsOn(nonWasmJsMain)
                implementation(libs.ktor.okhttp)
            }
        }

        val androidMain by getting {
            dependencies {
                dependsOn(nonWasmJsMain)
                implementation(libs.ktor.okhttp)
            }
        }

        val jsMain by getting {
            dependencies {
                dependsOn(nonWasmJsMain)
                implementation(libs.ktor.js)
            }
        }

        val wasmJs by creating {  }
    }
}