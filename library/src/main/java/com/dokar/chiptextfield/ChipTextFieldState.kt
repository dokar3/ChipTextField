package com.dokar.chiptextfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import kotlin.math.max

@Composable
fun <T : Chip> rememberChipTextFieldState(
    chips: List<T> = emptyList()
): ChipInputFieldState<T> {
    return remember(chips) {
        ChipInputFieldState(chips)
    }.also { state ->
        LaunchedEffect(state, state.disposed) {
            if (state.disposed) {
                state.chips = chips
                state.disposed = false
            }
        }
    }
}

class ChipInputFieldState<T : Chip>(
    chips: List<T> = emptyList()
) {
    internal var disposed = false

    var textFieldValue by mutableStateOf(TextFieldValue())

    var chips by mutableStateOf(chips)

    fun indexOf(chip: T): Int = chips.indexOf(chip)

    fun previousIndex(chip: T): Int = max(0, indexOf(chip) - 1)

    fun addChip(chip: T) {
        val list = chips.toMutableList()
        list.add(chip)
        chips = list
    }

    fun removeChip(chip: T) {
        val list = chips.toMutableList()
        list.remove(chip)
        chips = list
    }

    fun removeLastChip() {
        val list = chips.subList(0, chips.size - 1)
        chips = list
    }

    fun clearChips() {
        chips = emptyList()
    }
}