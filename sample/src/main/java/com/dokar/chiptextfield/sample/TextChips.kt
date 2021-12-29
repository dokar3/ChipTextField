package com.dokar.chiptextfield.sample

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dokar.chiptextfield.ChipStyle
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.rememberChipTextFieldState
import com.dokar.chiptextfield.sample.data.SampleChips

@Composable
internal fun TextChips(
    name: String,
    chipFieldStyle: ChipFieldStyle
) {
    val chips = remember { SampleChips.getTextChips() }
    val state = rememberChipTextFieldState(chips = chips)
    ChipsHeader("Text chips")
    ChipTextField(
        state = state,
        onCreateChip = ::Chip,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        initialTextFieldValue = name,
        textStyle = TextStyle.Default.copy(fontSize = 18.sp),
        cursorColor = chipFieldStyle.cursorColor,
        indicatorColor = chipFieldStyle.cursorColor,
        chipStyle = ChipStyle.Default.copy(
            textColor = chipFieldStyle.textColor,
            borderColor = chipFieldStyle.borderColor,
            backgroundColor = chipFieldStyle.backgroundColor
        )
    )
}