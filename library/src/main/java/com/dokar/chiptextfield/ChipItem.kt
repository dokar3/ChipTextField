package com.dokar.chiptextfield

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun ChipItem(
    chip: Chip,
    textColor: Color,
    borderColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    shape: Shape = CircleShape
) {
    Box {
        Row(
            modifier = modifier
                .background(backgroundColor)
                .border(1.dp, borderColor, shape)
                .clip(shape)
                .padding(6.dp, 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = chip.name, color = textColor)

            if (chip.showDelete) {
                Spacer(modifier = Modifier.width(4.dp))
                CloseButton(onClick = onCloseClick)
            }
        }
    }
}

@Composable
private fun CloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val padding = with(LocalDensity.current) { 6.dp.toPx() }
    val strokeWidth = with(LocalDensity.current) { 1.2.dp.toPx() }
    val backgroundColor = if (MaterialTheme.colors.isLight) {
        Color.Black.copy(alpha = 0.3f)
    } else {
        Color.White.copy(alpha = 0.3f)
    }
    val viewConfiguration = ViewConfigurationOverride(
        base = LocalViewConfiguration.current,
        minimumTouchTargetSize = DpSize(24.dp, 24.dp)
    )
    CompositionLocalProvider(LocalViewConfiguration provides viewConfiguration) {
        Canvas(
            modifier = modifier
                .then(Modifier.size(18.dp))
                .clip(CircleShape)
                .background(backgroundColor)
                .clickable(onClick = onClick)
        ) {
            drawLine(
                color = Color.White,
                start = Offset(padding, padding),
                end = Offset(size.width - padding, size.height - padding),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color.White,
                start = Offset(padding, size.height - padding),
                end = Offset(size.width - padding, padding),
                strokeWidth = strokeWidth
            )
        }
    }
}

internal class ViewConfigurationOverride(
    base: ViewConfiguration,
    override val doubleTapMinTimeMillis: Long = base.doubleTapMinTimeMillis,
    override val doubleTapTimeoutMillis: Long = base.doubleTapTimeoutMillis,
    override val longPressTimeoutMillis: Long = base.longPressTimeoutMillis,
    override val touchSlop: Float = base.touchSlop,
    override val minimumTouchTargetSize: DpSize = base.minimumTouchTargetSize
) : ViewConfiguration