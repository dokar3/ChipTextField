package com.dokar.chiptextfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

/**
 * Return a new remembered [ChipTextFieldState]
 *
 * @param value The value of text field.
 * @param onValueChange Called when the value in ChipTextField has changed.
 * @param chips Default chips
 */
@Composable
fun <T : Chip> rememberChipTextFieldState(
    value: String,
    onValueChange: (String) -> Unit,
    chips: List<T> = emptyList()
): ChipTextFieldState<T> {
    // Copied from BasicTextField.kt
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)
    var lastTextValue by remember(value) { mutableStateOf(value) }
    val mappedOnValueChange: (TextFieldValue) -> Unit = { newTextFieldValueState ->
        textFieldValueState = newTextFieldValueState

        val stringChangedSinceLastInvocation = lastTextValue != newTextFieldValueState.text
        lastTextValue = newTextFieldValueState.text

        if (stringChangedSinceLastInvocation) {
            onValueChange(newTextFieldValueState.text)
        }
    }

    return remember {
        ChipTextFieldState(textFieldValue, mappedOnValueChange, chips)
    }.apply {
        this.onValueChange = mappedOnValueChange
        this.value = textFieldValue
        this.defaultChips = chips
    }
}

/**
 * Return a new remembered [ChipTextFieldState]
 *
 * @param value The value of text field.
 * @param onValueChange Called when the value in ChipTextField has changed.
 * @param chips Default chips
 */
@Composable
fun <T : Chip> rememberChipTextFieldState(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    chips: List<T> = emptyList()
): ChipTextFieldState<T> {
    return remember {
        ChipTextFieldState(value, onValueChange, chips)
    }.apply {
        this.onValueChange = {
            if (it != value) {
                onValueChange(it)
            }
        }
        this.value = value
        this.defaultChips = chips
    }
}

/**
 * State class for [BasicChipTextField]
 *
 * @param value The value of text field.
 * @param onValueChange The callback to update value.
 * @param chips Default chips
 */
@Stable
class ChipTextFieldState<T : Chip>(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    chips: List<T> = emptyList()
) {
    internal var disposed = false

    private var _value by mutableStateOf(value)
    internal var value: TextFieldValue
        get() = _value
        set(newValue) {
            _value = newValue
            onValueChange(newValue)
        }

    internal var onValueChange by mutableStateOf(onValueChange)

    internal var defaultChips: List<T> = chips

    internal var focusedChip: T? by mutableStateOf(null)

    internal var nextFocusedChipIndex by mutableStateOf(-1)

    internal var recordFocusedChip = true

    var chips by mutableStateOf(chips)

    /**
     * Add a chip
     */
    fun addChip(chip: T) {
        val list = chips.toMutableList()
        list.add(chip)
        chips = list
    }

    /**
     * Remove chip
     */
    fun removeChip(chip: T) {
        val list = chips.toMutableList()
        val index = list.indexOf(chip)
        if (index == -1) {
            return
        }

        val focusedChipIndex = list.indexOf(focusedChip)
        if (focusedChipIndex == list.lastIndex && focusedChipIndex > 0) {
            if (index == list.lastIndex) {
                // The chip to remove is also the last chip, change the focused chip to the
                // previous chip
                focusedChip = list[index - 1]
            }
            // IME will lose focus if the focused chip is the last, we move the focus to the
            // previous composable to avoid IME flash
            recordFocusedChip = false
            nextFocusedChipIndex = focusedChipIndex - 1
        } else if (chip == focusedChip && index < list.lastIndex) {
            // We are removing the focusing chip, simply set the focused chip to the next one.
            focusedChip = list[index + 1]
        }

        list.remove(chip)
        chips = list
    }

    internal fun removeLastChip() {
        val list = chips.subList(0, chips.size - 1)
        chips = list
    }
}