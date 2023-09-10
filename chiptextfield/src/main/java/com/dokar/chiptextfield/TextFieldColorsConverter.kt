package com.dokar.chiptextfield

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color

fun TextFieldColors.toChipTextFieldColors(): ChipTextFieldColors {
    return object : ChipTextFieldColors {
        @Composable
        override fun textColor(
            enabled: Boolean,
            isError: Boolean,
            interactionSource: InteractionSource,
        ): State<Color> {
            return this@toChipTextFieldColors.textColor(enabled)
        }

        @Composable
        override fun cursorColor(isError: Boolean): State<Color> {
            return this@toChipTextFieldColors.cursorColor(isError)
        }

        @Composable
        override fun backgroundColor(
            enabled: Boolean,
            isError: Boolean,
            interactionSource: InteractionSource
        ): State<Color> {
            return this@toChipTextFieldColors.backgroundColor(enabled)
        }
    }
}