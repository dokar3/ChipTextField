plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
    alias(libs.plugins.compose.compiler)
}

kotlin {
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        iosMain  {
            dependencies {
                implementation(project(":sample:shared"))
            }
        }

        //may be imported?
        //kotlinx-coroutines-core-iosarm32/                                -         -
        //kotlinx-coroutines-core-iosarm64/                                -         -
        //kotlinx-coroutines-core-iossimulatorarm64/                       -         -
        //kotlinx-coroutines-core-iosx64/
    }
}
