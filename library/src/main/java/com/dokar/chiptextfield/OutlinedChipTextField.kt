package com.dokar.chiptextfield

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.util.runIf

/**
 * Chip text field with Material Design outlined style.
 *
 * @see [BasicChipTextField]
 * @see [OutlinedTextField]
 */
@Composable
fun <T : Chip> OutlinedChipTextField(
    state: ChipTextFieldState<T>,
    onSubmit: (value: String) -> T?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    readOnlyChips: Boolean = readOnly,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    textStyle: TextStyle = LocalTextStyle.current,
    chipStyle: ChipStyle = ChipTextFieldDefaults.chipStyle(),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    chipVerticalSpacing: Dp = 4.dp,
    chipHorizontalSpacing: Dp = 4.dp,
    chipLeadingIcon: @Composable (chip: T) -> Unit = {},
    chipTrailingIcon: @Composable (chip: T) -> Unit = { CloseButton(state, it) },
    onChipClick: ((chip: T) -> Unit)? = null,
    onChipLongClick: ((chip: T) -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
) {
    var value by remember { mutableStateOf(TextFieldValue()) }
    val onValueChange: (TextFieldValue) -> Unit = { value = it }
    OutlinedChipTextField(
        state = state,
        onSubmit = { onSubmit(it.text) },
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        readOnlyChips = readOnlyChips,
        isError = isError,
        keyboardOptions = keyboardOptions,
        textStyle = textStyle,
        chipStyle = chipStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        chipVerticalSpacing = chipVerticalSpacing,
        chipHorizontalSpacing = chipHorizontalSpacing,
        chipLeadingIcon = chipLeadingIcon,
        chipTrailingIcon = chipTrailingIcon,
        onChipClick = onChipClick,
        onChipLongClick = onChipLongClick,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}

/**
 * Chip text field with Material Design outlined style.
 *
 * @see [BasicChipTextField]
 * @see [OutlinedTextField]
 */
@Composable
fun <T : Chip> OutlinedChipTextField(
    state: ChipTextFieldState<T>,
    onSubmit: (value: String) -> T?,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    readOnlyChips: Boolean = readOnly,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    textStyle: TextStyle = LocalTextStyle.current,
    chipStyle: ChipStyle = ChipTextFieldDefaults.chipStyle(),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    chipVerticalSpacing: Dp = 4.dp,
    chipHorizontalSpacing: Dp = 4.dp,
    chipLeadingIcon: @Composable (chip: T) -> Unit = {},
    chipTrailingIcon: @Composable (chip: T) -> Unit = { CloseButton(state, it) },
    onChipClick: ((chip: T) -> Unit)? = null,
    onChipLongClick: ((chip: T) -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
) {
    // Copied from androidx.compose.foundation.text.BasicTextField.kt
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)
    SideEffect {
        if (textFieldValue.selection != textFieldValueState.selection ||
            textFieldValue.composition != textFieldValueState.composition) {
            textFieldValueState = textFieldValue
        }
    }
    var lastTextValue by remember(value) { mutableStateOf(value) }
    val mappedOnValueChange: (TextFieldValue) -> Unit = { newTextFieldValueState ->
        textFieldValueState = newTextFieldValueState

        val stringChangedSinceLastInvocation = lastTextValue != newTextFieldValueState.text
        lastTextValue = newTextFieldValueState.text

        if (stringChangedSinceLastInvocation) {
            onValueChange(newTextFieldValueState.text)
        }
    }
    OutlinedChipTextField(
        state = state,
        onSubmit = { onSubmit(it.text) },
        value = textFieldValue,
        onValueChange = mappedOnValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        readOnlyChips = readOnlyChips,
        isError = isError,
        keyboardOptions = keyboardOptions,
        textStyle = textStyle,
        chipStyle = chipStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        chipVerticalSpacing = chipVerticalSpacing,
        chipHorizontalSpacing = chipHorizontalSpacing,
        chipLeadingIcon = chipLeadingIcon,
        chipTrailingIcon = chipTrailingIcon,
        onChipClick = onChipClick,
        onChipLongClick = onChipLongClick,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}

/**
 * Chip text field with Material Design outlined style.
 *
 * @see [BasicChipTextField]
 * @see [OutlinedTextField]
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Chip> OutlinedChipTextField(
    state: ChipTextFieldState<T>,
    onSubmit: (value: TextFieldValue) -> T?,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    readOnlyChips: Boolean = readOnly,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    textStyle: TextStyle = LocalTextStyle.current,
    chipStyle: ChipStyle = ChipTextFieldDefaults.chipStyle(),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    chipVerticalSpacing: Dp = 4.dp,
    chipHorizontalSpacing: Dp = 4.dp,
    chipLeadingIcon: @Composable (chip: T) -> Unit = {},
    chipTrailingIcon: @Composable (chip: T) -> Unit = { CloseButton(state, it) },
    onChipClick: ((chip: T) -> Unit)? = null,
    onChipLongClick: ((chip: T) -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
) {
    Box(
        modifier = modifier
            .runIf(label != null) { modifier.padding(top = 8.dp) }
            .background(colors.backgroundColor(enabled).value, shape)
    ) {
        BasicChipTextField(
            state = state,
            onSubmit = onSubmit,
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            readOnlyChips = readOnlyChips,
            isError = isError,
            keyboardOptions = keyboardOptions,
            textStyle = textStyle,
            chipStyle = chipStyle,
            chipVerticalSpacing = chipVerticalSpacing,
            chipHorizontalSpacing = chipHorizontalSpacing,
            chipLeadingIcon = chipLeadingIcon,
            chipTrailingIcon = chipTrailingIcon,
            onChipClick = onChipClick,
            onChipLongClick = onChipLongClick,
            interactionSource = interactionSource,
            colors = colors,
            decorationBox = { innerTextField ->
                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    value = if (state.chips.isEmpty() && value.text.isEmpty()) "" else " ",
                    innerTextField = innerTextField,
                    enabled = !readOnly,
                    singleLine = false,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    isError = isError,
                    label = label,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    colors = colors,
                    border = {
                        TextFieldDefaults.BorderBox(
                            enabled,
                            isError,
                            interactionSource,
                            colors,
                            shape
                        )
                    },
                )
            },
        )
    }
}
