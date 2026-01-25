import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
    alias(libs.plugins.compose.compiler)
}

kotlin {
    js(IR) {
        outputModuleName.set("chiptextfield-sample")
        browser {
            commonWebpackConfig {
                outputFileName = "chiptextfield-sample.js"
            }
        }
        binaries.executable()
    }

    wasmJs {
        outputModuleName.set("chiptextfield-wasmjs-sample")
        binaries.executable()
        browser {
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    // Uncomment and configure this if you want to open a browser different from the system default
                    // open = mapOf(
                    //     "app" to mapOf(
                    //         "name" to "google chrome"
                    //     )
                    // )

                    // Serve sources to debug inside browser
                    static(project.rootDir.path)
                }
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

