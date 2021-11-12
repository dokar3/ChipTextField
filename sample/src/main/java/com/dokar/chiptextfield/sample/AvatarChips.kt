package com.dokar.chiptextfield.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.rememberChipTextFieldState
import com.dokar.chiptextfield.sample.data.AvatarChip
import com.dokar.chiptextfield.sample.data.SampleChips

@Composable
internal fun AvatarChips(
    name: String,
    chipColors: ChipColors
) {
    val chips = remember { SampleChips.getAvatarChips() }
    val state = rememberChipTextFieldState(chips = chips)
    ChipsHeader("Avatar chips")
    ChipTextField(
        state = state,
        onCreateChip = { AvatarChip(it, SampleChips.randomAvatarUrl()) },
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        initialTextFieldValue = name,
        textStyle = TextStyle.Default.copy(fontSize = 18.sp),
        cursorColor = chipColors.border,
        indicatorColor = chipColors.border,
        chipTextColor = chipColors.text,
        chipBorderColor = chipColors.border,
        chipBackgroundColor = chipColors.background,
        chipStartWidget = { Avatar(it) },
    )
}

@Composable
private fun BoxWithConstraintsScope.Avatar(
    chip: AvatarChip,
    modifier: Modifier = Modifier
) {
    val size = with(LocalDensity.current) { constraints.maxHeight.toDp() }
    val painter = rememberImagePainter(chip.avatarUrl)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .size(size)
            .clip(shape = CircleShape)
            .background(MaterialTheme.colors.onBackground.copy(alpha = 0.2f))
    )
}
