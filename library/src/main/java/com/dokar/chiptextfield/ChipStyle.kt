package com.dokar.chiptextfield

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

class ChipStyle(
    val shape: Shape = CircleShape,
    val textColor: Color = Color.Black,
    val borderColor: Color = textColor,
    val backgroundColor: Color = Color.Transparent
) {
    fun copy(
        shape: Shape = this.shape,
        textColor: Color = this.textColor,
        borderColor: Color = this.borderColor,
        backgroundColor: Color = this.backgroundColor
    ): ChipStyle {
        return ChipStyle(
            shape = shape,
            textColor = textColor,
            borderColor = borderColor,
            backgroundColor = backgroundColor
        )
    }

    companion object {
        val Default = ChipStyle()
    }
}