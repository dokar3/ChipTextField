package com.dokar.chiptextfield.sample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
internal fun TextChips(
    chipFieldStyle: ChipFieldStyle
) {
    ChipsHeader("Text chips")
    UnderlineStyleChips(chipFieldStyle)

    Spacer(modifier = Modifier.height(8.dp))

    ChipsHeader("Material outlined")
    MaterialOutlinedChips(chipFieldStyle)

    Spacer(modifier = Modifier.height(8.dp))

    ChipsHeader("Material filled")
    MaterialFilledChips(chipFieldStyle)
}

@Composable
private fun UnderlineStyleChips(chipFieldStyle: ChipFieldStyle) {
    val chips = remember { SampleChips.getTextChips() }
    var value by remember { mutableStateOf("Android") }
    val state = rememberChipTextFieldState(
        value = value,
        onValueChange = { value = it },
        chips = chips,
    )
    ChipTextField(
        state = state,
        modifier = Modifier.padding(8.dp),
        readOnlyChips = true,
        onSubmit = {
            state.addChip(Chip(value))
            value = ""
        },
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
}

@Composable
private fun MaterialOutlinedChips(chipFieldStyle: ChipFieldStyle) {
    val chips = remember { SampleChips.getTextChips() }
    var value by remember { mutableStateOf("Android") }
    val state = rememberChipTextFieldState(
        value = value,
        onValueChange = { value = it },
        chips = chips,
    )
    OutlinedChipTextField(
        state = state,
        modifier = Modifier.padding(8.dp),
        onSubmit = {
            state.addChip(Chip(value))
            value = ""
        },
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

@Composable
private fun MaterialFilledChips(chipFieldStyle: ChipFieldStyle) {
    val chips = remember { SampleChips.getTextChips() }
    var value by remember { mutableStateOf("Android") }
    val state = rememberChipTextFieldState(
        value = value,
        onValueChange = { value = it },
        chips = chips,
    )
    ChipTextField(
        state = state,
        modifier = Modifier.padding(8.dp),
        onSubmit = {
            state.addChip(Chip(value))
            value = ""
        },
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
}