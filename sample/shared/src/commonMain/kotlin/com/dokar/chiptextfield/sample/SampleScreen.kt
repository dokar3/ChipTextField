package com.dokar.chiptextfield.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.fetch.NetworkFetcher
import com.dokar.chiptextfield.sample.m2.M2ChipScreen
import com.dokar.chiptextfield.sample.m3.M3ChipsScreen

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SampleScreen(modifier: Modifier = Modifier) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(NetworkFetcher.Factory())
            }
            .build()
    }

    var sampleType by remember { mutableStateOf(SampleType.Material2) }

    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            when (sampleType) {
                SampleType.Material2 -> M2ChipScreen()
                SampleType.Material3 -> M3ChipsScreen()
            }
        }

        SampleTabLayout(
            type = sampleType,
            onSelectTab = { sampleType = it },
        )
    }
}

@Composable
private fun SampleTabLayout(
    type: SampleType,
    onSelectTab: (SampleType) -> Unit,
    modifier: Modifier = Modifier
) {
    val types = SampleType.entries

    val selectedIndex = types.indexOf(type)

    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = Color(0xFF5DA25D),
    ) {
        types.forEachIndexed { index, sampleType ->
            val isSelected = index == selectedIndex
            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelectTab(sampleType) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFF70B670),
                ),
                icon = {
                    Text(
                        text = sampleType.name,
                        color = Color.White,
                    )
                },
            )
        }
    }
}

enum class SampleType {
    Material2,
    Material3,
}