package com.dokar.chiptextfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.util.filterNewLine
import com.dokar.chiptextfield.widget.CloseButtonWidget
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <T : Chip> ChipTextField(
    state: ChipInputFieldState<T>,
    onCreateChip: (text: String) -> T,
    modifier: Modifier = Modifier,
    initialTextFieldValue: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = MaterialTheme.colors.onBackground,
    cursorColor: Color = MaterialTheme.colors.primary,
    indicatorColor: Color = MaterialTheme.colors.primary,
    chipVerticalSpacing: Dp = 4.dp,
    chipHorizontalSpacing: Dp = 4.dp,
    chipShape: Shape = CircleShape,
    chipTextColor: Color = textColor,
    chipBorderColor: Color = chipTextColor,
    chipBackgroundColor: Color = Color.Transparent,
    chipStartWidget: @Composable BoxWithConstraintsScope.(chip: T) -> Unit = {},
    chipEndWidget: @Composable BoxWithConstraintsScope.(chip: T) -> Unit = { chip: T ->
        CloseButtonWidget(state = state, chip = chip)
    },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(text = initialTextFieldValue))
    }
    val textFieldValue = textFieldValueState.copy(text = textFieldValueState.text)

    val fieldTextStyle = remember(textStyle, textColor) { textStyle.copy(color = textColor) }

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
            .padding(bottom = chipVerticalSpacing + 2.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    focusRequester.requestFocus()
                    // Move cursor to end
                    val text = textFieldValue.text
                    textFieldValueState = TextFieldValue(
                        text = text,
                        selection = TextRange(text.length, text.length)
                    )
                }
            ),
        mainAxisSpacing = chipHorizontalSpacing,
        crossAxisSpacing = chipVerticalSpacing,
        crossAxisAlignment = FlowCrossAxisAlignment.Center
    ) {
        ChipGroup(
            state = state,
            interactionSource = interactionSource,
            textStyle = textStyle,
            chipShape = chipShape,
            chipTextColor = chipTextColor,
            chipBorderColor = chipBorderColor,
            chipBackgroundColor = chipBackgroundColor,
            chipStartWidget = chipStartWidget,
            chipEndWidget = chipEndWidget
        )

        BasicTextField(
            value = textFieldValue,
            onValueChange = filterNewLine { value, hasNewLine ->
                textFieldValueState = if (hasNewLine) {
                    // Add chip
                    state.addChip(chip = onCreateChip(value.text))
                    TextFieldValue()
                } else {
                    value
                }
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
            textStyle = fieldTextStyle,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    val valueText = textFieldValue.text
                    if (valueText.isEmpty()) {
                        return@KeyboardActions
                    }
                    // Add chip
                    state.addChip(chip = onCreateChip(valueText))
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
private fun <T : Chip> ChipGroup(
    state: ChipInputFieldState<T>,
    interactionSource: MutableInteractionSource,
    textStyle: TextStyle,
    chipShape: Shape,
    chipTextColor: Color,
    chipBorderColor: Color,
    chipBackgroundColor: Color,
    chipStartWidget: @Composable BoxWithConstraintsScope.(chip: T) -> Unit,
    chipEndWidget: @Composable BoxWithConstraintsScope.(chip: T) -> Unit
) {
    for (chip in state.chips) {
        ChipItem(
            chip = chip,
            interactionSource = interactionSource,
            textStyle = textStyle,
            shape = chipShape,
            textColor = chipTextColor,
            borderColor = chipBorderColor,
            backgroundColor = chipBackgroundColor,
            chipStartWidget = chipStartWidget,
            chipEndWidget = chipEndWidget
        )
    }
}

@Composable
private fun <T : Chip> ChipItem(
    chip: T,
    interactionSource: MutableInteractionSource,
    textStyle: TextStyle,
    shape: Shape,
    textColor: Color,
    borderColor: Color,
    backgroundColor: Color,
    chipStartWidget: @Composable BoxWithConstraintsScope.(chip: T) -> Unit,
    chipEndWidget: @Composable BoxWithConstraintsScope.(chip: T) -> Unit,
    modifier: Modifier = Modifier
) {
    var textFieldValueState by remember(chip) { mutableStateOf(TextFieldValue(chip.value)) }
    val textFieldValue = textFieldValueState.copy(text = textFieldValueState.text)

    val chipTextStyle = remember(textColor) { textStyle.copy(color = textColor) }

    val focusRequester = remember { FocusRequester() }

    var textFieldHeight by remember { mutableStateOf(0) }

    val density = LocalDensity.current
    val textFieldHeightDp = remember(textFieldHeight) { with(density) { textFieldHeight.toDp() } }

    Row(
        modifier = modifier
            .clip(shape = shape)
            .background(color = backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .clickable { focusRequester.requestFocus() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        BoxWithConstraints(
            modifier = Modifier.height(textFieldHeightDp),
            contentAlignment = Alignment.Center
        ) {
            chipStartWidget(chip)
        }

        BasicTextField(
            value = textFieldValue,
            onValueChange = filterNewLine { value, hasNewLine ->
                textFieldValueState = value
                chip.value = value.text
                if (hasNewLine) {
                    focusRequester.freeFocus()
                }
            },
            modifier = Modifier
                .onSizeChanged { textFieldHeight = it.height }
                .padding(horizontal = 8.dp, vertical = 3.dp)
                .width(IntrinsicSize.Min)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { focusRequester.freeFocus() }),
            textStyle = chipTextStyle,
            interactionSource = interactionSource
        )

        BoxWithConstraints(
            modifier = Modifier.height(textFieldHeightDp),
            contentAlignment = Alignment.Center
        ) {
            chipEndWidget(chip)
        }
    }
}
