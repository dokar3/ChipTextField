package com.dokar.chiptextfield.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.ChipInputFieldState

@Composable
fun <T : Chip> CloseButtonWidget(
    state: ChipInputFieldState<T>,
    chip: T
) {
    Row {
        CloseButton(onClick = { state.removeChip(chip) })
        Spacer(modifier = Modifier.width(6.dp))
    }
}

@Composable
fun CloseButton(
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
                .size(18.dp)
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
