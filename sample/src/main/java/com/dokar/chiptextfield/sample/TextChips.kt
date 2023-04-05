package com.dokar.chiptextfield.sample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
internal fun TextChips(chipFieldStyle: ChipFieldStyle) {
    ChipsHeader("Text chips")
    Underline(chipFieldStyle)

    Spacer(modifier = Modifier.height(8.dp))

    ChipsHeader("Material outlined")
    MaterialOutlined(chipFieldStyle)

    Spacer(modifier = Modifier.height(8.dp))

    ChipsHeader("Material filled")
    MaterialFilled(chipFieldStyle)

    ChipsHeader("Scrollable")
    Scrollable(chipFieldStyle)
}

@Composable
private fun Underline(chipFieldStyle: ChipFieldStyle) {
    var value by remember { mutableStateOf("") }
    val state = rememberChipTextFieldState(chips = emptyList())
    ChipTextField(
        state = state,
        value = value,
        onValueChange = { value = it },
        onSubmit = ::Chip,
        modifier = Modifier.padding(8.dp),
        placeholder = { Text("Enter to submit a chip") },
        chipStyle = ChipTextFieldDefaults.chipStyle(
            focusedTextColor = chipFieldStyle.textColor,
            focusedBorderColor = chipFieldStyle.borderColor,
            focusedBackgroundColor = chipFieldStyle.backgroundColor,
        ),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = chipFieldStyle.cursorColor,
            focusedIndicatorColor = chipFieldStyle.cursorColor,
            backgroundColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(bottom = 8.dp),
    )
}

@Composable
private fun MaterialOutlined(chipFieldStyle: ChipFieldStyle) {
    val state = rememberChipTextFieldState(
        chips = remember { SampleChips.getTextChips() },
    )
    OutlinedChipTextField(
        state = state,
        onSubmit = ::Chip,
        modifier = Modifier.padding(8.dp),
        chipStyle = ChipTextFieldDefaults.chipStyle(
            focusedTextColor = chipFieldStyle.textColor,
            focusedBorderColor = chipFieldStyle.borderColor,
            focusedBackgroundColor = chipFieldStyle.backgroundColor,
        ),
        label = { Text("Label here") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = chipFieldStyle.cursorColor,
            focusedBorderColor = chipFieldStyle.cursorColor,
            focusedLabelColor = chipFieldStyle.cursorColor,
        ),
    )
}

@Composable
private fun MaterialFilled(chipFieldStyle: ChipFieldStyle) {
    val state = rememberChipTextFieldState(
        chips = remember { SampleChips.getTextChips() },
    )
    ChipTextField(
        state = state,
        modifier = Modifier.padding(8.dp),
        onSubmit = ::Chip,
        chipStyle = ChipTextFieldDefaults.chipStyle(
            focusedTextColor = chipFieldStyle.textColor,
            focusedBorderColor = chipFieldStyle.borderColor,
            focusedBackgroundColor = chipFieldStyle.backgroundColor,
        ),
        label = { Text("Label here") },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = chipFieldStyle.cursorColor,
            focusedIndicatorColor = chipFieldStyle.cursorColor,
            focusedLabelColor = chipFieldStyle.cursorColor,
        ),
    )
}

@Composable
internal fun Scrollable(chipFieldStyle: ChipFieldStyle) {
    val initialChips = remember { SampleChips.getTextChips() + SampleChips.getTextChips() }

    val filledState = rememberChipTextFieldState(chips = initialChips)

    val outlinedState = rememberChipTextFieldState(chips = initialChips)

    ChipTextField(
        state = filledState,
        modifier = Modifier.padding(8.dp),
        innerModifier = Modifier
            .heightIn(max = 100.dp)
            .verticalScroll(state = rememberScrollState()),
        onSubmit = ::Chip,
        chipStyle = ChipTextFieldDefaults.chipStyle(
            focusedTextColor = chipFieldStyle.textColor,
            focusedBorderColor = chipFieldStyle.borderColor,
            focusedBackgroundColor = chipFieldStyle.backgroundColor,
        ),
        label = { Text("Label here") },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = chipFieldStyle.cursorColor,
            focusedIndicatorColor = chipFieldStyle.cursorColor,
            focusedLabelColor = chipFieldStyle.cursorColor,
        ),
    )

    OutlinedChipTextField(
        state = outlinedState,
        onSubmit = ::Chip,
        modifier = Modifier.padding(8.dp),
        innerModifier = Modifier
            .heightIn(max = 100.dp)
            .verticalScroll(state = rememberScrollState()),
        chipStyle = ChipTextFieldDefaults.chipStyle(
            focusedTextColor = chipFieldStyle.textColor,
            focusedBorderColor = chipFieldStyle.borderColor,
            focusedBackgroundColor = chipFieldStyle.backgroundColor,
        ),
        label = { Text("Label here") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = chipFieldStyle.cursorColor,
            focusedBorderColor = chipFieldStyle.cursorColor,
            focusedLabelColor = chipFieldStyle.cursorColor,
        ),
    )
}
