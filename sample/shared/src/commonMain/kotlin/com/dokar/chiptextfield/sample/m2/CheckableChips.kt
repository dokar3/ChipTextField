package com.dokar.chiptextfield.sample.m2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.BasicChipTextField
import com.dokar.chiptextfield.ChipTextFieldDefaults
import com.dokar.chiptextfield.rememberChipTextFieldState
import com.dokar.chiptextfield.sample.ChipFieldStyle
import com.dokar.chiptextfield.sample.data.CheckableChip
import com.dokar.chiptextfield.sample.data.SampleChips
import com.dokar.chiptextfield.toChipTextFieldColors

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
internal fun CheckableChips(
    chipFieldStyle: ChipFieldStyle
) {
    val state = rememberChipTextFieldState(chips = SampleChips.checkable)

    ChipsHeader("Checkable chips")

    BasicChipTextField(
        state = state,
        onSubmit = { null },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        readOnly = true,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = chipFieldStyle.cursorColor
        ).toChipTextFieldColors(),
        chipStyle = ChipTextFieldDefaults.chipStyle(
            focusedTextColor = chipFieldStyle.textColor,
            focusedBorderColor = chipFieldStyle.borderColor,
            focusedBackgroundColor = chipFieldStyle.backgroundColor,
        ),
        chipLeadingIcon = { CheckIcon(it, chipFieldStyle) },
        chipTrailingIcon = {},
        onChipClick = { it.isChecked = !it.isChecked },
    )
}

@Composable
private fun CheckIcon(
    chip: CheckableChip,
    chipFieldStyle: ChipFieldStyle,
    modifier: Modifier = Modifier
) {
    if (chip.isChecked) {
        Image(
            painter = rememberVectorPainter(Icons.Outlined.Check),
            contentDescription = null,
            modifier = modifier
                .size(24.dp)
                .padding(start = 6.dp),
            colorFilter = ColorFilter.tint(chipFieldStyle.textColor)
        )
    }
}