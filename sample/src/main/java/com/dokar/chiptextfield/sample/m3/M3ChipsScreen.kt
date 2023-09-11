package com.dokar.chiptextfield.sample.m3

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.m3.ChipTextField
import com.dokar.chiptextfield.m3.OutlinedChipTextField
import com.dokar.chiptextfield.rememberChipTextFieldState
import com.dokar.chiptextfield.sample.data.SampleChips

@Composable
fun M3ChipsScreen(modifier: Modifier = Modifier) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = rememberScrollState()),
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                ChipsHeader(name = "Filled")
                MaterialFilled()

                Spacer(modifier = Modifier.height(32.dp))

                ChipsHeader(name = "Outlined")
                MaterialOutlined()
            }
        }
    }
}

@Composable
private fun MaterialFilled(modifier: Modifier = Modifier) {
    val state = rememberChipTextFieldState(chips = SampleChips.text)
    ChipTextField(
        state = state,
        modifier = modifier.padding(8.dp),
        onSubmit = ::Chip,
        label = { Text("Label here") },
    )
}

@Composable
private fun MaterialOutlined(modifier: Modifier = Modifier) {
    val state = rememberChipTextFieldState(chips = SampleChips.text)
    OutlinedChipTextField(
        state = state,
        onSubmit = ::Chip,
        modifier = modifier.padding(8.dp),
        label = { Text("Label here") },
    )
}
