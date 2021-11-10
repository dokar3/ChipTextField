package com.dokar.chiptextfield.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.rememberChipInputFieldState
import com.dokar.chiptextfield.sample.theme.ChipTextFieldTheme

private const val LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipisicing elit"

private data class ChipColors(
    val text: Color,
    val border: Color,
    val background: Color
)

private val THEME_COLORS = listOf(
    ChipColors(text = Color.Black, border = Color.Black, background = Color.Transparent),
    ChipColors(text = Color.White, border = Color(0xff94d2bd), background = Color(0xff94d2bd)),
    ChipColors(text = Color.White, border = Color(0xffe85d04), background = Color(0xffe85d04)),
    ChipColors(text = Color.White, border = Color(0xff9fa0ff), background = Color(0xff9fa0ff))
)

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
        val chips = remember { LOREM_IPSUM.split(" ").map(::Chip) }
        val state = rememberChipInputFieldState(chips = chips)
        ChipTextField(
            state = state,
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            initialTextFieldValue = name,
            cursorColor = chipColors.border,
            indicatorColor = chipColors.border,
            chipTextColor = chipColors.text,
            chipBorderColor = chipColors.border,
            chipBackgroundColor = chipColors.background
        )

        Spacer(modifier = Modifier.height(32.dp))

        ThemeColorSelector(
            selectedPosition = selectedColorPosition,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ThemeColorSelector(
    selectedPosition: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for ((index, colors) in THEME_COLORS.withIndex()) {
            val chipColors = if (index == 0) {
                getDefaultChipColors()
            } else {
                colors
            }
            ColorItem(
                color = chipColors.border,
                isSelected = index == selectedPosition.value,
                modifier = Modifier.clickable { selectedPosition.value = index }
            )
        }
    }
}

@Composable
private fun ColorItem(
    color: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) {
        color
    } else {
        Color.Transparent
    }
    Box(
        modifier.size(32.dp)
            .clip(CircleShape)
            .border(width = 2.dp, color = borderColor, shape = CircleShape)
            .padding(4.dp)
            .background(color = color, shape = CircleShape)
    )
}

@Composable
private fun getDefaultChipColors(): ChipColors {
    return if (MaterialTheme.colors.isLight) {
        THEME_COLORS[0]
    } else {
        ChipColors(
            text = Color.White,
            border = Color.White,
            background = Color.Transparent
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