package com.dokar.chiptextfield

import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.PlatformTextStyle
import kotlinx.browser.window
import kotlinx.coroutines.awaitAnimationFrame

actual suspend fun awaitFrame() {
    // May not be needed?
    window.awaitAnimationFrame()
}

internal actual val DefaultPlatformTextStyle: PlatformTextStyle
    get() = PlatformTextStyle(
        spanStyle = PlatformSpanStyle.Default,
        paragraphStyle = PlatformParagraphStyle.Default,
    )