package com.dokar.chiptextfield.util

import androidx.compose.ui.Modifier

internal inline fun Modifier.combineIf(
    value: Boolean,
    combined: (Modifier) -> Modifier
): Modifier {
    return if (value) {
        combined(this)
    } else {
        this
    }
}