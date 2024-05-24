// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val composeBomVersion: String by extra("2024.02.01")
    val composeCompilerVersion: String by extra("1.5.11")
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.mavenpublish) apply false
}
