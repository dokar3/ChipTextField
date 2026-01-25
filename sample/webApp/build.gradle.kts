plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
    alias(libs.plugins.compose.compiler)
}

kotlin {
    js(IR) {
        outputModuleName.set("webApp")
        browser {
            commonWebpackConfig {
                outputFileName = "webApp.js"
            }
        }
        binaries.executable()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set("webAppWasm")
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "webAppWasm.js"
            }

            // Uncomment the next line to apply Binaryen and get optimized wasm binaries
            // applyBinaryen()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":sample:shared"))
                implementation(libs.jetbrains.compose.resources)
            }
        }
    }
}

