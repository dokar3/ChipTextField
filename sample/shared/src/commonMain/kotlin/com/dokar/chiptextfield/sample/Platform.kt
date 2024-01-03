package com.dokar.chiptextfield.sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun setupImageLoader()

@Composable
expect fun AsyncImage(
    model: Any,
    contentDescription: String?,
    modifier: Modifier = Modifier,
)
