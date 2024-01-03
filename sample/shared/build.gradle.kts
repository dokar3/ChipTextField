plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.dokar.chiptextfield.sample.shared"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }
    androidTarget()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":chiptextfield-core"))
                api(project(":chiptextfield"))
                api(project(":chiptextfield-m3"))
                api(compose.material)
                api(compose.material3)
                implementation(libs.coil)
            }
        }
    }
}