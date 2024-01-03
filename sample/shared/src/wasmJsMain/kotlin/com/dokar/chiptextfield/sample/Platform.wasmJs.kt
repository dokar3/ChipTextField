package com.dokar.chiptextfield.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun setupImageLoader() {
}

@Composable
actual fun AsyncImage(
    model: Any,
    contentDescription: String?,
    modifier: Modifier,
) {
    // Until coil3 has the wasm target
    Box(modifier = modifier)
}