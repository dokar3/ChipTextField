package com.dokar.chiptextfield

import androidx.compose.ui.text.PlatformTextStyle

expect suspend fun awaitFrame()

internal expect val DefaultPlatformTextStyle: PlatformTextStyle