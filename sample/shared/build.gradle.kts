plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.dokar.chiptextfield.sample.shared"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    jvm()
    js().browser()
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