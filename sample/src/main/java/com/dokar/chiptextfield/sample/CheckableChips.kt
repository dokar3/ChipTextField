package com.dokar.chiptextfield.sample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.BasicChipTextField
import com.dokar.chiptextfield.ChipTextFieldDefaults
import com.dokar.chiptextfield.rememberChipTextFieldState
import com.dokar.chiptextfield.sample.data.CheckableChip
import com.dokar.chiptextfield.sample.data.SampleChips

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
internal fun CheckableChips(
    chipFieldStyle: ChipFieldStyle
) {
    val state = rememberChipTextFieldState(
        chips = remember { SampleChips.getCheckableChips() }
    )

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
        ),
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
            painter = painterResource(R.drawable.ic_baseline_check_24),
            contentDescription = null,
            modifier = modifier
                .size(24.dp)
                .padding(start = 6.dp),
            colorFilter = ColorFilter.tint(chipFieldStyle.textColor)
        )
    }
}