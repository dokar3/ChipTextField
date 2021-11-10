package com.dokar.chiptextfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberChipInputFieldState(chips: List<Chip>): ChipInputFieldState {
    return remember {
        ChipInputFieldState(chips)
    }
}

class ChipInputFieldState(
    _chips: List<Chip> = emptyList()
) {
    var chips by mutableStateOf(_chips)

    fun addChip(chip: Chip) {
        val list = chips.toMutableList()
        list.add(chip)
        chips = list
    }

    fun removeChip(chip: Chip) {
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