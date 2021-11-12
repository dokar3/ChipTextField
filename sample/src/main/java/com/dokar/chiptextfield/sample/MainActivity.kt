package com.dokar.chiptextfield.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.sample.theme.ChipTextFieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChipTextFieldTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SampleScreen("Android")
                }
            }
        }
    }
}

@Composable
fun SampleScreen(name: String) {
    val selectedColorPosition = remember {
        mutableStateOf(0)
    }

    val chipColors = when (selectedColorPosition.value) {
        0 -> {
            getDefaultChipColors()
        }
        else -> {
            THEME_COLORS[selectedColorPosition.value]
        }
    }

    Column {
        TextChips(name = name, chipColors = chipColors)

        Spacer(modifier = Modifier.height(32.dp))

        CheckableChips(name = name, chipColors = chipColors)

        Spacer(modifier = Modifier.height(32.dp))

        AvatarChips(name = name, chipColors = chipColors)

        Spacer(modifier = Modifier.height(32.dp))

        ThemeColorSelector(
            selectedPosition = selectedColorPosition,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChipTextFieldTheme {
        SampleScreen("Android")
    }
}