package com.dokar.chiptextfield.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dokar.chiptextfield.ChipStyle
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.rememberChipTextFieldState
import com.dokar.chiptextfield.sample.data.AvatarChip
import com.dokar.chiptextfield.sample.data.SampleChips

@Composable
internal fun AvatarChips(
    name: String,
    chipFieldStyle: ChipFieldStyle
) {
    val chips = remember { SampleChips.getAvatarChips() }
    val state = rememberChipTextFieldState(chips = chips)
    ChipsHeader("Avatar chips")
    ChipTextField(
        state = state,
        onCreateChip = { AvatarChip(it, SampleChips.randomAvatarUrl()) },
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
        ),
        chipLeadingIcon = { Avatar(it) },
    )
}

@Composable
private fun Avatar(
    chip: AvatarChip,
    modifier: Modifier = Modifier
) {
    val painter = rememberImagePainter(chip.avatarUrl)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .size(32.dp)
            .clip(shape = CircleShape)
            .background(MaterialTheme.colors.onBackground.copy(alpha = 0.2f))
    )
}
