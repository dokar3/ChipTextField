plugins {
    alias(libs.plugins.android.kotlin.multiplatform)
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(11)

    androidLibrary {
        namespace = "com.dokar.chiptextfield.sample.shared"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        androidResources.enable = true
    }

    jvm()

    js(IR) {
        browser()
    }
    wasmJs {
        browser()
        binaries.executable()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":chiptextfield-core"))
                api(project(":chiptextfield"))
                api(project(":chiptextfield-m3"))
                api(libs.jetbrains.compose.material)
                api(libs.jetbrains.compose.material3)
                implementation(libs.jetbrains.compose.resources)
            }
        }

        val nonWasmJsMain by creating {
            dependsOn(commonMain)
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