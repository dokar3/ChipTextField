package com.dokar.chiptextfield.util

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type

internal inline fun Modifier.runIf(
    value: Boolean,
    block: Modifier.() -> Modifier
): Modifier {
    return if (value) {
        this.block()
    } else {
        this
    }
}

@OptIn(ExperimentalComposeUiApi::class)
internal inline fun Modifier.onBackspaceUp(
    crossinline action: () -> Unit
): Modifier {
    return onKeyEvent {
        if (it.type == KeyEventType.KeyUp && it.key == Key.Backspace) {
            action()
        }
        false
    }
}