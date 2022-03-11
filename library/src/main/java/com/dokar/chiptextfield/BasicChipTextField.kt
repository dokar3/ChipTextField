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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
    ExperimentalMaterialApi::class,
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
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
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
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }

    val focusRequester = remember { FocusRequester() }

    val editable = enabled && !readOnly

    val keyboardController = LocalSoftwareKeyboardController.current

    val emptyTextField = remember(state.textFieldValue) {
        state.textFieldValue.text.isEmpty()
    }

    fun createNewChip(value: TextFieldValue): Boolean {
        val newChip = onCreateChip(value.text)
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
            ChipGroup(
                state = state,
                enabled = enabled,
                readOnly = readOnly,
                keyboardOptions = keyboardOptions,
                onChipClick = onChipClick,
                onChipLongClick = onChipLongClick,
                interactionSource = interactionSource,
                newChipFieldFocusRequester = focusRequester,
                textStyle = textStyle,
                chipStyle = chipStyle,
                chipLeadingIcon = chipLeadingIcon,
                chipTrailingIcon = chipTrailingIcon,
            )

            BasicTextField(
                value = state.textFieldValue,
                onValueChange = filterNewLine { value, hasNewLine ->
                    if (hasNewLine && value.text.isNotEmpty()) {
                        // Add chip
                        if (createNewChip(value)) {
                            return@filterNewLine
                        }
                    }
                    if (value.text.isEmpty()) {
                        // Fix new lines cannot be trimmed
                        state.textFieldValue = TextFieldValue("\b")
                    }
                    state.textFieldValue = value
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onBackspaceUp {
                        if (emptyTextField && state.chips.isNotEmpty()) {
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
                            createNewChip(state.textFieldValue)
                        }
                    }
                ),
                interactionSource = interactionSource,
                cursorBrush = SolidColor(colors.cursorColor(isError).value),
            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
private fun <T : Chip> ChipGroup(
    state: ChipTextFieldState<T>,
    enabled: Boolean,
    readOnly: Boolean,
    keyboardOptions: KeyboardOptions,
    onChipClick: ((chip: T) -> Unit)?,
    onChipLongClick: ((chip: T) -> Unit)?,
    interactionSource: MutableInteractionSource,
    newChipFieldFocusRequester: FocusRequester,
    textStyle: TextStyle,
    chipStyle: ChipStyle,
    chipLeadingIcon: @Composable (chip: T) -> Unit,
    chipTrailingIcon: @Composable (chip: T) -> Unit
) {
    val focusedItem = remember { mutableStateOf(-1) }
    for (chip in state.chips) {
        ChipItem(
            state = state,
            chip = chip,
            focusedItem = focusedItem,
            enabled = enabled,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            onClick = onChipClick,
            onLongClick = onChipLongClick,
            interactionSource = interactionSource,
            newChipFieldFocusRequester = newChipFieldFocusRequester,
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
    state: ChipTextFieldState<T>,
    chip: T,
    focusedItem: MutableState<Int>,
    enabled: Boolean,
    readOnly: Boolean,
    keyboardOptions: KeyboardOptions,
    onClick: ((chip: T) -> Unit)?,
    onLongClick: ((chip: T) -> Unit)?,
    interactionSource: MutableInteractionSource,
    newChipFieldFocusRequester: FocusRequester,
    textStyle: TextStyle,
    chipStyle: ChipStyle,
    chipLeadingIcon: @Composable (chip: T) -> Unit,
    chipTrailingIcon: @Composable (chip: T) -> Unit,
    modifier: Modifier = Modifier
) {
    var textFieldValueState by remember(chip) { mutableStateOf(TextFieldValue(chip.text)) }

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

    val emptyTextField = remember(chip.text) {
        chip.text.isEmpty()
    }

    LaunchedEffect(chip, focusedItem.value) {
        if (focusedItem.value == state.indexOf(chip)) {
            focusRequester.requestFocus()
            textFieldValueState = textFieldValueState.copy(
                selection = TextRange(textFieldValueState.text.length)
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
            value = textFieldValueState,
            onValueChange = filterNewLine { value, hasNewLine ->
                textFieldValueState = value
                chip.text = value.text
                if (hasNewLine) {
                    focusRequester.freeFocus()
                    newChipFieldFocusRequester.requestFocus()
                }
            },
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(horizontal = 8.dp, vertical = 3.dp)
                .focusRequester(focusRequester)
                .onBackspaceUp {
                    if (emptyTextField) {
                        focusedItem.value = state.previousIndex(chip)
                        state.removeChip(chip)
                    }
                },
            keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Done),
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
