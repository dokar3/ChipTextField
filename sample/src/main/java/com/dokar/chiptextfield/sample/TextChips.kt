package com.dokar.chiptextfield.sample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.ChipTextFieldDefaults
import com.dokar.chiptextfield.OutlinedChipTextField
import com.dokar.chiptextfield.rememberChipTextFieldState
import com.dokar.chiptextfield.sample.data.SampleChips

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
internal fun TextChips(
    chipFieldStyle: ChipFieldStyle
) {
    val chips = remember { SampleChips.getTextChips() }
    ChipsHeader("Text chips")
    ChipTextField(
        state = rememberChipTextFieldState(chips = chips),
        onCreateChip = ::Chip,
        modifier = Modifier.padding(8.dp),
        initialTextFieldValue = "Android",
        chipStyle = ChipTextFieldDefaults.chipStyle(
            textColor = chipFieldStyle.textColor,
            borderColor = chipFieldStyle.borderColor,
            backgroundColor = chipFieldStyle.backgroundColor
        ),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = chipFieldStyle.cursorColor,
            focusedIndicatorColor = chipFieldStyle.cursorColor,
            backgroundColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(bottom = 8.dp),
    )

    Spacer(modifier = Modifier.height(8.dp))

    ChipsHeader("Material filled")
    ChipTextField(
        state = rememberChipTextFieldState(chips = chips),
        onCreateChip = ::Chip,
        modifier = Modifier.padding(8.dp),
        chipStyle = ChipTextFieldDefaults.chipStyle(
            textColor = chipFieldStyle.textColor,
            borderColor = chipFieldStyle.borderColor,
            backgroundColor = chipFieldStyle.backgroundColor
        ),
        label = { Text("Label here") },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = chipFieldStyle.cursorColor,
            focusedIndicatorColor = chipFieldStyle.cursorColor,
            focusedLabelColor = chipFieldStyle.cursorColor,
        ),
    )

    Spacer(modifier = Modifier.height(8.dp))

    ChipsHeader("Material outlined")
    OutlinedChipTextField(
        state = rememberChipTextFieldState(chips = chips),
        onCreateChip = ::Chip,
        modifier = Modifier.padding(8.dp),
        chipStyle = ChipTextFieldDefaults.chipStyle(
            textColor = chipFieldStyle.textColor,
            borderColor = chipFieldStyle.borderColor,
            backgroundColor = chipFieldStyle.backgroundColor
        ),
        label = { Text("Label here") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = chipFieldStyle.cursorColor,
            focusedBorderColor = chipFieldStyle.cursorColor,
            focusedLabelColor = chipFieldStyle.cursorColor,
        ),
    )
}