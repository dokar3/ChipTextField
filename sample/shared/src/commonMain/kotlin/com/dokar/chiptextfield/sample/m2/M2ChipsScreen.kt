package com.dokar.chiptextfield.sample.m2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.sample.CHIP_TEXT_FILED_STYLES
import com.dokar.chiptextfield.sample.ChipFieldStyle
import com.dokar.chiptextfield.sample.ThemeColorSelector
import com.dokar.chiptextfield.sample.getDefaultChipFieldStyle
import com.dokar.chiptextfield.sample.theme.ChipTextFieldTheme

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun M2ChipScreen(modifier: Modifier = Modifier) {
    ChipTextFieldTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.background,
            modifier = modifier.fillMaxSize()
        ) {
            val selectedColorPosition = remember { mutableIntStateOf(0) }

            val chipFieldStyle = getChipFieldStyle(selectedColorPosition.intValue)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                ThemeColorSelector(
                    selectedPosition = selectedColorPosition,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                TextChips(chipFieldStyle = chipFieldStyle)

                Spacer(modifier = Modifier.height(32.dp))

                CheckableChips(chipFieldStyle = chipFieldStyle)

                Spacer(modifier = Modifier.height(32.dp))

                AvatarChips(chipFieldStyle = chipFieldStyle)

                Spacer(modifier = Modifier.height(32.dp))

                ManualFocusChips(chipFieldStyle = chipFieldStyle)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun getChipFieldStyle(selectedPos: Int): ChipFieldStyle {
    return when (selectedPos) {
        0 -> {
            getDefaultChipFieldStyle()
        }

        else -> {
            CHIP_TEXT_FILED_STYLES[selectedPos]
        }
    }
}
