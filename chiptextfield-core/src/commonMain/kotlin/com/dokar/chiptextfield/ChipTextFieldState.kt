package com.dokar.chiptextfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue

/**
 * Return a new remembered [ChipTextFieldState]
 *
 * @param chips Default chips
 */
@Composable
fun <T : Chip> rememberChipTextFieldState(
    chips: List<T> = emptyList()
): ChipTextFieldState<T> {
    return remember { ChipTextFieldState(chips = chips) }
}

/**
 * Return a new remembered [ChipTextFieldState], recreating it when [key] changes.
 *
 * @param chips Default chips
 * @param key An input that identifies the state. When this value changes, the previous state is
 * discarded and a new state is created with [chips].
 */
@Composable
fun <T : Chip> rememberChipTextFieldState(
    chips: List<T> = emptyList(),
    key: Any?,
): ChipTextFieldState<T> {
    return remember(key) { ChipTextFieldState(chips = chips) }
}

/**
 * Return a [ChipTextFieldState] that is retained after leaving the composition.
 *
 * Retained state can survive configuration changes, but it does not survive process death. Use
 * [rememberChipTextFieldState] when the state should only live as long as the current composition.
 *
 * @param chips Default chips
 */
@Composable
fun <T : Chip> retainChipTextFieldState(
    chips: List<T> = emptyList(),
): ChipTextFieldState<T> {
    return retain { retainedChipTextFieldState(chips) }
}

/**
 * Return a [ChipTextFieldState] that is retained after leaving the composition, recreating it when
 * [key] changes.
 *
 * Retained state can survive configuration changes, but it does not survive process death. Use
 * [rememberChipTextFieldState] when the state should only live as long as the current composition.
 *
 * @param chips Default chips
 * @param key An input that identifies the retained state. When this value changes, the previous
 * state is discarded and a new state is created with [chips].
 */
@Composable
fun <T : Chip> retainChipTextFieldState(
    chips: List<T> = emptyList(),
    key: Any?,
): ChipTextFieldState<T> {
    return retain(key) { retainedChipTextFieldState(chips) }
}

private fun <T : Chip> retainedChipTextFieldState(chips: List<T>): ChipTextFieldState<T> {
    return ChipTextFieldState(chips = chips).apply {
        resetChipsAfterLeavingComposition = false
    }
}

/**
 * State class for [BasicChipTextField]
 *
 * @param chips Default chips
 */
@Stable
class ChipTextFieldState<T : Chip>(
    chips: List<T> = emptyList()
) {
    internal var resetChipsAfterLeavingComposition = true

    internal var disposed = false

    internal var defaultChips: List<T> = chips

    private var _focusedChip: T? by mutableStateOf(null)
    internal val focusedChip get() = _focusedChip

    private var _focusedChipIndex by mutableIntStateOf(-1)
    val focusedChipIndex get() = _focusedChipIndex

    internal var nextFocusedChipIndex by mutableIntStateOf(-1)

    internal var recordFocusedChip = true

    private var nextTextFieldFocusRequestId = 0

    internal var textFieldFocusRequest by mutableStateOf(TextFieldFocusRequest())

    private var _isTextFieldFocused by mutableStateOf(false)
    val isTextFieldFocused get() = _isTextFieldFocused

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
                updateFocusedChip(list[index - 1])
            }
            // IME will lose focus if the focused chip is the last, we move the focus to the
            // previous composable to avoid IME flash
            recordFocusedChip = false
            nextFocusedChipIndex = focusedChipIndex - 1
        } else if (chip == focusedChip && index < list.lastIndex) {
            // We are removing the focusing chip, simply set the focused chip to the next one.
            updateFocusedChip(list[index + 1])
        }

        list.remove(chip)
        chips = list
    }

    internal fun removeLastChip() {
        val list = chips.subList(0, chips.size - 1)
        chips = list
    }

    internal fun updateFocusedChip(chip: T?) {
        if (chip != null) {
            textFieldFocusRequest = textFieldFocusRequest.copy(
                state = TextFieldFocusState.None,
            )
        }
        this._focusedChip = chip
        this._focusedChipIndex = chips.indexOf(chip)
    }

    internal fun updateTextFieldFocus(isFocused: Boolean) {
        _isTextFieldFocused = isFocused
    }

    internal fun consumeTextFieldFocusRequest(request: TextFieldFocusRequest) {
        if (textFieldFocusRequest == request) {
            textFieldFocusRequest = request.copy(state = TextFieldFocusState.None)
        }
    }

    /**
     * Focus a chip by index.
     */
    fun focusChip(index: Int) {
        if (chips.isEmpty()) return
        if (index < 0 || index > chips.lastIndex) return
        updateFocusedChip(chips[index])
    }

    /**
     * Clear focus from a focused chip by index.
     */
    fun clearChipFocus(index: Int) {
        if (chips.isEmpty()) return
        if (index < 0 || index > chips.lastIndex) return
        val target = chips[index]
        if (focusedChip == target) {
            updateFocusedChip(null)
        }
    }

    /**
     * Focus the text field at the end of chips.
     */
    fun focusTextField() {
        updateTextFieldFocusRequest(TextFieldFocusState.Focused)
    }

    /**
     * Clear focus from the focused text field at the end chips.
     */
    fun clearTextFieldFocus() {
        updateTextFieldFocusRequest(TextFieldFocusState.Unfocused)
    }

    private fun updateTextFieldFocusRequest(state: TextFieldFocusState) {
        textFieldFocusRequest = TextFieldFocusRequest(
            id = ++nextTextFieldFocusRequestId,
            state = state,
        )
    }
}

internal data class TextFieldFocusRequest(
    val id: Int = 0,
    val state: TextFieldFocusState = TextFieldFocusState.None,
)

internal enum class TextFieldFocusState {
    None,
    Focused,
    Unfocused,
}
