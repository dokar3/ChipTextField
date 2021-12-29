package com.dokar.chiptextfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import kotlin.math.max

/**
 * Return a new remembered [ChipTextFieldState]
 */
@Composable
fun <T : Chip> rememberChipTextFieldState(
    chips: List<T> = emptyList()
): ChipTextFieldState<T> {
    return remember(chips) {
        ChipTextFieldState(chips)
    }.apply {
        this.defaultChips = chips
    }
}

/**
 * State class for [ChipTextField]
 *
 * @param chips Default chips
 */
class ChipTextFieldState<T : Chip>(
    chips: List<T> = emptyList()
) {
    internal var disposed = false

    internal var defaultChips: List<T> = chips

    var textFieldValue by mutableStateOf(TextFieldValue())

    var chips by mutableStateOf(chips)

    internal fun indexOf(chip: T): Int = chips.indexOf(chip)

    internal fun previousIndex(chip: T): Int = max(0, indexOf(chip) - 1)

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
        list.remove(chip)
        chips = list
    }

    internal fun removeLastChip() {
        val list = chips.subList(0, chips.size - 1)
        chips = list
    }

    /**
     * Clear all chips
     */
    fun clearChips() {
        chips = emptyList()
    }
}