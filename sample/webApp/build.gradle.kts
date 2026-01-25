plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    js(IR) {
        compilerOptions {
            outputModuleName.set("webApp")
        }
        browser {
            commonWebpackConfig {
                outputFileName = "webApp.js"
            }
        }
        binaries.executable()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        compilerOptions {
            outputModuleName.set("webAppWasm")
        }
        browser {
            commonWebpackConfig {
                outputFileName = "webAppWasm.js"
            }
            // Uncomment the next line to apply Binaryen and get optimized wasm binaries
            // applyBinaryen()
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":sample:shared"))
        }
    }
}

