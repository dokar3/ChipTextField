@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package com.dokar.chiptextfield.m3

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.dokar.chiptextfield.ChipTextFieldColors

fun TextFieldColors.toChipTextFieldColors(): ChipTextFieldColors {
    return object : ChipTextFieldColors {
        @Composable
        override fun textColor(
            enabled: Boolean,
            isError: Boolean,
            interactionSource: InteractionSource
        ): State<Color> {
            return this@toChipTextFieldColors.textColor(
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
            )
        }

        @Composable
        override fun cursorColor(isError: Boolean): State<Color> {
            return this@toChipTextFieldColors.cursorColor(isError = isError)
        }

        @Composable
        override fun backgroundColor(
            enabled: Boolean,
            isError: Boolean,
            interactionSource: InteractionSource
        ): State<Color> {
            return this@toChipTextFieldColors.containerColor(
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
            )
        }
    }
}
