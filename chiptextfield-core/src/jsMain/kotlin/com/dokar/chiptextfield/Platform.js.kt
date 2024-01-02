package com.dokar.chiptextfield

import kotlinx.browser.window
import kotlinx.coroutines.awaitAnimationFrame

actual suspend fun awaitFrame() {
    // May not be needed?
    window.awaitAnimationFrame()
}