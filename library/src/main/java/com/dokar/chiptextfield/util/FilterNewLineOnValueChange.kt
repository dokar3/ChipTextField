package com.dokar.chiptextfield.util

import androidx.compose.ui.text.input.TextFieldValue

/**
 * Remove all `\n` in [TextFieldValue]
 */
internal fun filterNewLine(
    block: (value: TextFieldValue, hasNewLine: Boolean) -> Unit
): (TextFieldValue) -> Unit = {
    val text = it.text
    val hasNewLine = text.hasNewLine()
    val value = if (hasNewLine) {
        TextFieldValue(
            text = text.removeNewLine(),
            selection = it.selection,
            composition = it.composition,
        )
    } else {
        it
    }
    block(value, hasNewLine)
}

private fun String.hasNewLine(): Boolean {
    return indexOf('\n') != -1
}

private fun String.removeNewLine(): String {
    val index = indexOf('\n')
    return if (index != -1) {
        replace(NEW_LINE_REGEX, "")
    } else {
        this
    }
}

private val NEW_LINE_REGEX = Regex("\\n")
