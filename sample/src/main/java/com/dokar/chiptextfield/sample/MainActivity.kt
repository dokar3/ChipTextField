package com.dokar.chiptextfield.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.rememberChipInputFieldState
import com.dokar.chiptextfield.sample.theme.ChipTextFieldTheme

private const val LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipisicing elit"

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
    Column {
        val chips = remember {
            LOREM_IPSUM.split(" ").map(::Chip)
        }
        val state = rememberChipInputFieldState(chips = chips)
        ChipTextField(
            state = state,
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            initialTextFieldValue = name
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