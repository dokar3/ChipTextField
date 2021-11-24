package com.dokar.chiptextfield.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dokar.chiptextfield.ChipStyle
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.rememberChipTextFieldState
import com.dokar.chiptextfield.sample.data.CheckableChip
import com.dokar.chiptextfield.sample.data.SampleChips

@Composable
internal fun CheckableChips(
    name: String,
    chipFieldStyle: ChipFieldStyle
) {
    val chips = remember { SampleChips.getCheckableChips() }
    val state = rememberChipTextFieldState(chips = chips)
    ChipsHeader("Checkable chips")
    ChipTextField(
        state = state,
        onCreateChip = ::CheckableChip,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        initialTextFieldValue = name,
        readOnly = true,
        textStyle = TextStyle.Default.copy(fontSize = 18.sp),
        cursorColor = chipFieldStyle.cursorColor,
        indicatorColor = chipFieldStyle.cursorColor,
        chipStyle = ChipStyle.Default.copy(
            textColor = chipFieldStyle.textColor,
            borderColor = chipFieldStyle.borderColor,
            backgroundColor = chipFieldStyle.backgroundColor
        ),
        chipStartWidget = {
            if (it.isChecked) {
                CheckedIcon(chipFieldStyle = chipFieldStyle)
            }
        },
        chipEndWidget = {},
        onChipClick = {
            it.isChecked = !it.isChecked
        }
    )
}

@Composable
private fun CheckedIcon(
    chipFieldStyle: ChipFieldStyle,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.ic_baseline_check_24),
        contentDescription = null,
        modifier = modifier
            .size(24.dp)
            .padding(start = 6.dp),
        colorFilter = ColorFilter.tint(chipFieldStyle.textColor)
    )
}