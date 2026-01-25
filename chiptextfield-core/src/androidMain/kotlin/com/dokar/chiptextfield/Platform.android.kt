package com.dokar.chiptextfield

import androidx.compose.ui.text.PlatformTextStyle

actual suspend fun awaitFrame() {
    kotlinx.coroutines.android.awaitFrame()
}

internal actual val DefaultPlatformTextStyle: PlatformTextStyle
    get() = PlatformTextStyle()