package com.dokar.chiptextfield

import androidx.compose.ui.graphics.painter.Painter

data class Chip(
    val name: String,
    val icon: Painter? = null,
    val showDelete: Boolean = true,
)
