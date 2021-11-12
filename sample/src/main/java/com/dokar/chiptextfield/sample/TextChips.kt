package com.dokar.chiptextfield.sample

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.rememberChipTextFieldState
import com.dokar.chiptextfield.sample.data.SampleChips

@Composable
internal fun TextChips(
    name: String,
    chipColors: ChipColors
) {
    val chips = remember { SampleChips.getTextChips() }
    val state = rememberChipTextFieldState(chips = chips)
    ChipsHeader("Text chips")
    ChipTextField(
        state = state,
        onCreateChip = Chip::textChip,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        initialTextFieldValue = name,
        cursorColor = chipColors.border,
        indicatorColor = chipColors.border,
        chipTextColor = chipColors.text,
        chipBorderColor = chipColors.border,
        chipBackgroundColor = chipColors.background
    )
}