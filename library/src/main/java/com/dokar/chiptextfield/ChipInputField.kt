package com.dokar.chiptextfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChipTextField(
    state: ChipInputFieldState,
    modifier: Modifier = Modifier,
    initialTextFieldValue: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textColor: Color = MaterialTheme.colors.onBackground,
    cursorColor: Color = MaterialTheme.colors.primary,
    indicatorColor: Color = MaterialTheme.colors.primary,
    chipTextColor: Color = textColor,
    chipBorderColor: Color = chipTextColor,
    chipBackgroundColor: Color = Color.Transparent,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(text = initialTextFieldValue))
    }
    val textFieldValue = textFieldValueState.copy(text = textFieldValueState.text)

    val textStyle = remember(textColor) { TextStyle.Default.copy(color = textColor) }

    val focusRequester = remember { FocusRequester() }

    val isFocused by interactionSource.collectIsFocusedAsState()

    val indicatorWidth = when {
        isFocused -> IndicatorFocusedWidth
        else -> IndicatorUnfocusedWidth
    }

    val currIndicatorColor = when {
        isFocused -> indicatorColor
        else -> MaterialTheme.colors.onBackground.copy(alpha = 0.45f)
    }

    FlowRow(
        modifier = modifier
            .drawIndicatorLine(indicatorWidth, currIndicatorColor)
            .padding(bottom = 6.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    focusRequester.requestFocus()
                }
            ),
        mainAxisSpacing = 4.dp,
        crossAxisSpacing = 4.dp,
        crossAxisAlignment = FlowCrossAxisAlignment.Center
    ) {
        ChipGroup(
            state = state,
            chipTextColor = chipTextColor,
            chipBorderColor = chipBorderColor,
            chipBackgroundColor = chipBackgroundColor
        )

        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValueState = it
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .onKeyEvent {
                    if (it.type == KeyEventType.KeyUp
                        && it.key == Key.Backspace
                        && textFieldValue.text.isEmpty()
                        && state.chips.isNotEmpty()
                    ) {
                        // Remove previous chip
                        state.removeLastChip()
                    }
                    false
                },
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    val valueText = textFieldValue.text
                    if (valueText.isEmpty()) {
                        return@KeyboardActions
                    }
                    // Add chip
                    state.addChip(Chip(valueText))
                    textFieldValueState = TextFieldValue()
                }
            ),
            singleLine = true,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(cursorColor)
        )
    }
}

@Composable
private fun ChipGroup(
    state: ChipInputFieldState,
    chipTextColor: Color,
    chipBorderColor: Color,
    chipBackgroundColor: Color
) {
    for (chip in state.chips) {
        ChipItem(
            chip = chip,
            textColor = chipTextColor,
            borderColor = chipBorderColor,
            backgroundColor = chipBackgroundColor,
            onCloseClick = {
                state.removeChip(chip)
            }
        )
    }
}
