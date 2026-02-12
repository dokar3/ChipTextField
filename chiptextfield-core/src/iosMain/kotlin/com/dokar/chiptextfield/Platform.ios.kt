package com.dokar.chiptextfield

import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.PlatformTextStyle

actual suspend fun awaitFrame() {
    withFrameNanos {}
}

internal actual val DefaultPlatformTextStyle: PlatformTextStyle
    get() = PlatformTextStyle(
        spanStyle = PlatformSpanStyle.Default,
        paragraphStyle = PlatformParagraphStyle.Default,
    )
