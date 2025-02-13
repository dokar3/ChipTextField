package com.dokar.chiptextfield.sample
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.fetch.NetworkFetcher

@OptIn(ExperimentalCoilApi::class)
@Composable
actual fun setupImageLoader() {

    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(NetworkFetcher.Factory())
            }
            .build()
    }
}

@Composable
actual fun AsyncImage(model: Any, contentDescription: String?, modifier: Modifier) {
    coil3.compose.AsyncImage(model, contentDescription, modifier)
}