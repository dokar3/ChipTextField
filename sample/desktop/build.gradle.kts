plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(11)

    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":sample:shared"))
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.dokar.chiptextfield.sample.MainKt"
    }
}