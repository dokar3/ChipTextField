package com.dokar.chiptextfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.util.combineIf
import com.dokar.chiptextfield.util.filterNewLine
import com.dokar.chiptextfield.util.onBackspaceUp
import com.dokar.chiptextfield.widget.CloseButtonWidget
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <T : Chip> ChipTextField(
    state: ChipInputFieldState<T>,
    onCreateChip: (text: String) -> T?,
    modifier: Modifier = Modifier,
    initialTextFieldValue: String = "",
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = MaterialTheme.colors.onBackground,
    cursorColor: Color = MaterialTheme.colors.primary,
    indicatorColor: Color = cursorColor,
    chipStyle: ChipStyle = ChipStyle.Default,
    chipVerticalSpacing: Dp = 4.dp,
    chipHorizontalSpacing: Dp = 4.dp,
    chipStartWidget: @Composable (chip: T) -> Unit = {},
    chipEndWidget: @Composable (chip: T) -> Unit = { chip: T ->
        CloseButtonWidget(state = state, chip = chip)
    },
    onChipClick: (chip: T) -> Unit = {},
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

    val editable = !readOnly

    val keyboardController = LocalSoftwareKeyboardController.current

    FlowRow(
        modifier = modifier
            .combineIf(editable) {
                it
                    .drawIndicatorLine(indicatorWidth, currIndicatorColor)
                    .padding(bottom = chipVerticalSpacing + 4.dp)
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    if (editable) {
                        keyboardController?.show()
                        focusRequester.requestFocus()
                        // Move cursor to end
                        val text = textFieldValue.text
                        textFieldValueState = TextFieldValue(
                            text = text,
                            selection = TextRange(text.length, text.length)
                        )
                    }
                }
            ),
        mainAxisSpacing = chipHorizontalSpacing,
        crossAxisSpacing = chipVerticalSpacing,
        crossAxisAlignment = FlowCrossAxisAlignment.Center
    ) {
        ChipGroup(
            state = state,
            readOnly = readOnly,
            onChipClick = onChipClick,
            interactionSource = interactionSource,
            newChipFieldFocusRequester = focusRequester,
            textStyle = textStyle,
            chipStyle = chipStyle,
            chipStartWidget = chipStartWidget,
            chipEndWidget = chipEndWidget
        )

        if (editable) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = filterNewLine { value, hasNewLine ->
                    textFieldValueState = if (hasNewLine) {
                        // Add chip
                        val newChip = onCreateChip(value.text)
                        if (newChip != null) {
                            state.addChip(newChip)
                            TextFieldValue()
                        } else {
                            value
                        }
                    } else {
                        value
                    }
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onBackspaceUp {
                        if (textFieldValue.text.isEmpty()
                            && state.chips.isNotEmpty()
                        ) {
                            // Remove previous chip
                            state.removeLastChip()
                        }
                    },
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
                        val newChip = onCreateChip(valueText)
                        if (newChip != null) {
                            state.addChip(newChip)
                            textFieldValueState = TextFieldValue()
                        }
                    }
                ),
                singleLine = true,
                interactionSource = interactionSource,
                cursorBrush = SolidColor(cursorColor)
            )
        }
    }
}

@Composable
private fun <T : Chip> ChipGroup(
    state: ChipInputFieldState<T>,
    readOnly: Boolean,
    onChipClick: (chip: T) -> Unit,
    interactionSource: MutableInteractionSource,
    newChipFieldFocusRequester: FocusRequester,
    textStyle: TextStyle,
    chipStyle: ChipStyle,
    chipStartWidget: @Composable (chip: T) -> Unit,
    chipEndWidget: @Composable (chip: T) -> Unit
) {
    for (chip in state.chips) {
        ChipItem(
            state = state,
            chip = chip,
            readOnly = readOnly,
            onClick = onChipClick,
            interactionSource = interactionSource,
            newChipFieldFocusRequester = newChipFieldFocusRequester,
            textStyle = textStyle,
            chipStyle = chipStyle,
            chipStartWidget = chipStartWidget,
            chipEndWidget = chipEndWidget
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun <T : Chip> ChipItem(
    state: ChipInputFieldState<T>,
    chip: T,
    readOnly: Boolean,
    onClick: (chip: T) -> Unit,
    interactionSource: MutableInteractionSource,
    newChipFieldFocusRequester: FocusRequester,
    textStyle: TextStyle,
    chipStyle: ChipStyle,
    chipStartWidget: @Composable (chip: T) -> Unit,
    chipEndWidget: @Composable (chip: T) -> Unit,
    modifier: Modifier = Modifier
) {
    var textFieldValueState by remember(chip) { mutableStateOf(TextFieldValue(chip.value)) }
    val textFieldValue = textFieldValueState.copy(text = textFieldValueState.text)

    val chipTextStyle = remember(chipStyle) { textStyle.copy(color = chipStyle.textColor) }

    val focusRequester = remember { FocusRequester() }

    var textFieldHeight by remember { mutableStateOf(0) }

    val density = LocalDensity.current
    val textFieldHeightDp = remember(textFieldHeight) { with(density) { textFieldHeight.toDp() } }

    val keyboardController = LocalSoftwareKeyboardController.current

    val editable = !readOnly

    Row(
        modifier = modifier
            .clip(shape = chipStyle.shape)
            .background(color = chipStyle.backgroundColor)
            .border(width = 1.dp, color = chipStyle.borderColor, shape = chipStyle.shape)
            .clickable {
                if (editable) {
                    keyboardController?.show()
                    focusRequester.requestFocus()
                }
                onClick(chip)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.requiredHeight(32.dp),
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
                    newChipFieldFocusRequester.requestFocus()
                }
            },
            modifier = Modifier
                .onSizeChanged { textFieldHeight = it.height }
                .padding(horizontal = 8.dp, vertical = 3.dp)
                .width(IntrinsicSize.Min)
                .focusRequester(focusRequester)
                .onBackspaceUp {
                    if (textFieldValue.selection.start == 0
                        && textFieldValue.selection.end == 0
                    ) {
                        state.removeChip(chip)
                    }
                },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { focusRequester.freeFocus() }),
            enabled = editable,
            readOnly = readOnly,
            textStyle = chipTextStyle,
            interactionSource = interactionSource
        )

        Box(
            modifier = Modifier.requiredHeight(textFieldHeightDp),
            contentAlignment = Alignment.Center
        ) {
            chipEndWidget(chip)
        }
    }
}
