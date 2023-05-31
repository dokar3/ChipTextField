package com.dokar.chiptextfield.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
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
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    SampleScreen()
                }
            }
        }
    }
}

@OptIn(
    ExperimentalComposeUiApi::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)
@Composable
fun SampleScreen() {
    val selectedColorPosition = remember { mutableStateOf(0) }

    val chipFieldStyle = getChipFieldStyle(selectedColorPosition.value)

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChipTextFieldTheme {
        SampleScreen()
    }
}