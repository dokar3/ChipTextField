package com.dokar.chiptextfield.sample.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dokar.chiptextfield.Chip

class CheckableChip(
    override var value: String,
    checked: Boolean = false
) : Chip {
    var isChecked by mutableStateOf(checked)
}