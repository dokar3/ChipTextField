package com.dokar.chiptextfield

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Indicator style
 */
interface IndicatorStyle {
    @Composable
    fun height(readOnly: Boolean, interactionSource: InteractionSource): State<Dp>

    @Composable
    fun color(readOnly: Boolean, interactionSource: InteractionSource): State<Color>
}