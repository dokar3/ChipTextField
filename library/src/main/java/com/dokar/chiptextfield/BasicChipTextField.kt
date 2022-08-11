package com.dokar.chiptextfield

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.util.filterNewLine
import com.dokar.chiptextfield.util.onBackspaceUp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

/**
 * A text field can display chips, press enter to create a new chip.
 *
 * @param state Use [rememberChipTextFieldState] to create new state.
 * @param onCreateChip Create a new chip, will be called after pressing enter key. Return null to prevent creating new chip.
 * @param modifier Modifier for chip text field.
 * @param initialTextFieldValue Initial text field value.
 * @param enabled Enabled state, if false, user will not able to edit and select.
 * @param readOnly If true, edit will be disabled, but user can still select text.
 * @param isError Error state, it is used to change cursor color.
 * @param keyboardOptions See [BasicTextField] for the details.
 * @param textStyle Text style, also apply to text in chips.
 * @param chipStyle Chip style, include shape, text color, background color, etc. See [ChipStyle].
 * @param chipVerticalSpacing Vertical spacing between chips.
 * @param chipHorizontalSpacing Horizontal spacing between chips.
 * @param chipLeadingIcon Leading chip icon, nothing will be displayed by default.
 * @param chipTrailingIcon Trailing chip icon, by default, a [CloseButton] will be displayed.
 * @param onChipClick Chip click action.
 * @param onChipLongClick Chip long click action.
 * @param colors Text colors. [TextFieldDefaults.textFieldColors] is default colors.
 * @param decorationBox The decoration box to wrap around text field.
 *
 * @see BasicTextField
 */
@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun <T : Chip> BasicChipTextField(
    state: ChipTextFieldState<T>,
    onCreateChip: (text: String) -> T?,
    modifier: Modifier = Modifier,
    initialTextFieldValue: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = LocalTextStyle.current,
    chipStyle: ChipStyle = ChipTextFieldDefaults.chipStyle(),
    chipVerticalSpacing: Dp = 4.dp,
    chipHorizontalSpacing: Dp = 4.dp,
    chipLeadingIcon: @Composable (chip: T) -> Unit = {},
    chipTrailingIcon: @Composable (chip: T) -> Unit = { CloseButton(state, it) },
    onChipClick: ((chip: T) -> Unit)? = null,
    onChipLongClick: ((chip: T) -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() },
) {
    val focusRequester = remember { FocusRequester() }

    val editable = enabled && !readOnly

    val keyboardController = LocalSoftwareKeyboardController.current

    fun createNewChip(value: String): Boolean {
        val newChip = onCreateChip(value)
        return if (newChip != null) {
            state.addChip(newChip)
            state.textFieldValue = TextFieldValue()
            true
        } else {
            false
        }
    }

    LaunchedEffect(state, state.disposed) {
        if (state.disposed) {
            state.chips = state.defaultChips
            state.disposed = false
        }
        state.textFieldValue = TextFieldValue(initialTextFieldValue)
    }

    DisposableEffect(state) {
        onDispose {
            state.disposed = true
        }
    }

    decorationBox {
        FlowRow(
            modifier = modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        keyboardController?.show()
                        focusRequester.requestFocus()
                        // Move cursor to end
                        val selection = state.textFieldValue.text.length
                        state.textFieldValue = state.textFieldValue.copy(
                            selection = TextRange(selection)
                        )
                    },
                    enabled = editable,
                ),
            mainAxisSpacing = chipHorizontalSpacing,
            crossAxisSpacing = chipVerticalSpacing,
            crossAxisAlignment = FlowCrossAxisAlignment.Center
        ) {
            Chips(
                chips = state.chips,
                enabled = enabled,
                readOnly = readOnly,
                onRemoveRequest = { state.removeChip(it) },
                onChipFreeFocus = { focusRequester.requestFocus() },
                onChipClick = onChipClick,
                onChipLongClick = onChipLongClick,
                textStyle = textStyle,
                chipStyle = chipStyle,
                chipLeadingIcon = chipLeadingIcon,
                chipTrailingIcon = chipTrailingIcon,
            )

            Input(
                state = state,
                onCreateNewChip = ::createNewChip,
                enabled = enabled,
                readOnly = readOnly,
                isError = isError,
                textStyle = textStyle,
                colors = colors,
                keyboardOptions = keyboardOptions,
                focusRequester = focusRequester,
                interactionSource = interactionSource,
            )
        }
    }
}

@Composable
private fun <T : Chip> Input(
    state: ChipTextFieldState<T>,
    onCreateNewChip: (String) -> Boolean,
    enabled: Boolean,
    readOnly: Boolean,
    isError: Boolean,
    textStyle: TextStyle,
    colors: TextFieldColors,
    keyboardOptions: KeyboardOptions,
    focusRequester: FocusRequester,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    BasicTextField(
        value = state.textFieldValue,
        onValueChange = filterNewLine { value, hasNewLine ->
            if (hasNewLine && value.text.isNotEmpty()) {
                // Add chip
                if (onCreateNewChip(value.text)) {
                    return@filterNewLine
                }
            }
            if (value.text.isEmpty()) {
                // Fix new lines cannot be trimmed
                state.textFieldValue = TextFieldValue("\b")
            }
            state.textFieldValue = value
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .onBackspaceUp {
                if (state.textFieldValue.text.isEmpty() && state.chips.isNotEmpty()) {
                    // Remove previous chip
                    state.removeLastChip()
                }
            },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle.copy(color = textColor),
        keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                val valueText = state.textFieldValue.text
                if (valueText.isNotEmpty()) {
                    // Add chip
                    onCreateNewChip(state.textFieldValue.text)
                }
            }
        ),
        interactionSource = interactionSource,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
    )
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
private fun <T : Chip> Chips(
    chips: List<T>,
    enabled: Boolean,
    readOnly: Boolean,
    onRemoveRequest: (T) -> Unit,
    onChipFreeFocus: () -> Unit,
    onChipClick: ((chip: T) -> Unit)?,
    onChipLongClick: ((chip: T) -> Unit)?,
    textStyle: TextStyle,
    chipStyle: ChipStyle,
    chipLeadingIcon: @Composable (chip: T) -> Unit,
    chipTrailingIcon: @Composable (chip: T) -> Unit
) {
    var focusedChipIndex by remember { mutableStateOf(-1) }
    for ((index, chip) in chips.withIndex()) {
        ChipItem(
            isFocused = index == focusedChipIndex,
            chip = chip,
            enabled = enabled,
            readOnly = readOnly,
            onRemoveRequest = {
                focusedChipIndex = (index - 1).coerceAtLeast(0)
                onRemoveRequest(chip)
            },
            onFreeFocus = onChipFreeFocus,
            onClick = onChipClick,
            onLongClick = onChipLongClick,
            textStyle = textStyle,
            chipStyle = chipStyle,
            chipLeadingIcon = chipLeadingIcon,
            chipTrailingIcon = chipTrailingIcon
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
private fun <T : Chip> ChipItem(
    isFocused: Boolean,
    chip: T,
    enabled: Boolean,
    readOnly: Boolean,
    onRemoveRequest: () -> Unit,
    onFreeFocus: () -> Unit,
    onClick: ((chip: T) -> Unit)?,
    onLongClick: ((chip: T) -> Unit)?,
    textStyle: TextStyle,
    chipStyle: ChipStyle,
    chipLeadingIcon: @Composable (chip: T) -> Unit,
    chipTrailingIcon: @Composable (chip: T) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    var textFieldValue by remember(chip) { mutableStateOf(TextFieldValue(chip.text)) }

    val shape by chipStyle.shape(
        readOnly = readOnly,
        interactionSource = interactionSource,
    )

    val borderWidth by chipStyle.borderWidth(
        readOnly = readOnly,
        interactionSource = interactionSource,
    )

    val borderColor by chipStyle.borderColor(
        readOnly = readOnly,
        interactionSource = interactionSource,
    )

    val textColor by chipStyle.textColor(
        readOnly = readOnly,
        interactionSource = interactionSource,
    )
    val chipTextStyle = remember(textColor) { textStyle.copy(color = textColor) }

    val backgroundColor by chipStyle.backgroundColor(
        readOnly = readOnly,
        interactionSource = interactionSource,
    )

    val focusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    val editable = enabled && !readOnly

    LaunchedEffect(chip, isFocused) {
        if (isFocused) {
            focusRequester.requestFocus()
            textFieldValue = textFieldValue.copy(
                selection = TextRange(textFieldValue.text.length)
            )
        }
    }

    ChipItemLayout(
        leadingIcon = {
            chipLeadingIcon(chip)
        },
        trailingIcon = {
            chipTrailingIcon(chip)
        },
        modifier = modifier
            .clip(shape = shape)
            .background(color = backgroundColor)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = shape
            )
            .padding(borderWidth)
            .combinedClickable(
                onClick = {
                    if (editable) {
                        keyboardController?.show()
                        focusRequester.requestFocus()
                    }
                    onClick?.invoke(chip)
                },
                onLongClick = {
                    onLongClick?.invoke(chip)
                }
            ),
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = filterNewLine { value, hasNewLine ->
                textFieldValue = value
                chip.text = value.text
                if (hasNewLine) {
                    focusRequester.freeFocus()
                    onFreeFocus()
                }
            },
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(horizontal = 8.dp, vertical = 3.dp)
                .focusRequester(focusRequester)
                .onBackspaceUp {
                    if (chip.text.isEmpty()) {
                        onRemoveRequest()
                    }
                },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusRequester.freeFocus() }),
            singleLine = true,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = chipTextStyle,
            interactionSource = interactionSource
        )
    }
}

@Composable
private fun ChipItemLayout(
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = {
            Box { leadingIcon() }
            Box { content() }
            Box { trailingIcon() }
        },
    ) { measurables, constraints ->
        val maxWidth = constraints.maxWidth

        val leadingMeasurable = measurables[0]
        val contentMeasurable = measurables[1]
        val trailingMeasurable = measurables[2]

        var restWidth = maxWidth

        val leadingPlaceable = leadingMeasurable.measure(constraints = constraints)
        restWidth -= leadingPlaceable.width

        val trailingPlaceable = trailingMeasurable.measure(Constraints(maxWidth = restWidth))
        restWidth -= trailingPlaceable.width

        val contentPlaceable = contentMeasurable.measure(Constraints(maxWidth = restWidth))

        val width = leadingPlaceable.width + contentPlaceable.width + trailingPlaceable.width
        val height = maxOf(
            leadingPlaceable.height,
            contentPlaceable.height,
            trailingPlaceable.height,
        )

        val placeables = arrayOf(leadingPlaceable, contentPlaceable, trailingPlaceable)

        layout(width = width, height = height) {
            var x = 0
            for (placeable in placeables) {
                placeable.placeRelative(
                    x = x,
                    y = (height - placeable.height) / 2,
                )
                x += placeable.width
            }
        }
    }
}
