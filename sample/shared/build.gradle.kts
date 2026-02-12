plugins {
    alias(libs.plugins.android.kotlin.multiplatform)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.multiplatform)
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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    js(IR) {
        browser()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":chiptextfield-core"))
                implementation(project(":chiptextfield"))
                implementation(project(":chiptextfield-m3"))
                api(libs.jetbrains.compose.runtime)
                api(libs.jetbrains.compose.ui)
                api(libs.jetbrains.compose.foundation)
                api(libs.jetbrains.compose.material)
                api(libs.jetbrains.compose.material3)
                implementation(libs.coil.compose)
                implementation(libs.coil.network)
                implementation(libs.jetbrains.compose.resources)
            }
        }
    }
}