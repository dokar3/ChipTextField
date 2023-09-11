package com.dokar.chiptextfield.m3

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.dokar.chiptextfield.ChipTextFieldColors
import java.lang.reflect.Field

fun TextFieldColors.toChipTextFieldColors(colorScheme: ColorScheme): ChipTextFieldColors {
    val colors = this.readColors(colorScheme = colorScheme)
    return object : ChipTextFieldColors {
        @Composable
        override fun textColor(
            enabled: Boolean,
            isError: Boolean,
            interactionSource: InteractionSource
        ): State<Color> {
            val isFocused by interactionSource.collectIsFocusedAsState()
            return rememberUpdatedState(
                when {
                    !enabled -> colors.disabledTextColor
                    isError -> colors.errorTextColor
                    isFocused -> colors.focusedTextColor
                    else -> colors.unfocusedTextColor
                }
            )
        }

        @Composable
        override fun cursorColor(isError: Boolean): State<Color> {
            return rememberUpdatedState(
                if (isError) colors.errorCursorColor else colors.cursorColor
            )
        }

        @Composable
        override fun backgroundColor(
            enabled: Boolean,
            isError: Boolean,
            interactionSource: InteractionSource
        ): State<Color> {
            val isFocused by interactionSource.collectIsFocusedAsState()
            return rememberUpdatedState(
                when {
                    !enabled -> colors.disabledContainerColor
                    isError -> colors.errorContainerColor
                    isFocused -> colors.focusedContainerColor
                    else -> colors.unfocusedContainerColor
                }
            )
        }
    }
}

private class PartialTextFieldColors(
    val focusedTextColor: Color,
    val unfocusedTextColor: Color,
    val disabledTextColor: Color,
    val errorTextColor: Color,
    val focusedContainerColor: Color,
    val unfocusedContainerColor: Color,
    val disabledContainerColor: Color,
    val errorContainerColor: Color,
    val cursorColor: Color,
    val errorCursorColor: Color,
)

/**
 * We cannot read colors from [TextFieldColors] directly, fields are private and
 * functions are internal.
 */
private fun TextFieldColors.readColors(colorScheme: ColorScheme): PartialTextFieldColors {
    fun colorOf(field: Field) = Color(field.getLong(this).toULong())

    return try {
        val fields = textFieldColorsFields
        val clz = TextFieldColors::class.java
        for (field in clz.declaredFields) {
            field.isAccessible = true
        }
        PartialTextFieldColors(
            focusedTextColor = colorOf(fields.focusedTextColor),
            unfocusedTextColor = colorOf(fields.unfocusedTextColor),
            disabledTextColor = colorOf(fields.disabledTextColor),
            errorTextColor = colorOf(fields.errorTextColor),
            focusedContainerColor = colorOf(fields.focusedContainerColor),
            unfocusedContainerColor = colorOf(fields.unfocusedContainerColor),
            disabledContainerColor = colorOf(fields.disabledContainerColor),
            errorContainerColor = colorOf(fields.errorContainerColor),
            cursorColor = colorOf(fields.cursorColor),
            errorCursorColor = colorOf(fields.errorCursorColor),
        )
    } catch (e: NoSuchFieldError) {
        e.printStackTrace()
        PartialTextFieldColors(
            focusedTextColor = colorScheme.onSurface,
            unfocusedTextColor = colorScheme.onSurface,
            disabledTextColor = colorScheme.onSurface.copy(
                alpha = ChipTextFieldDefaults.disabledContentAlpha,
            ),
            errorTextColor = colorScheme.onSurface,
            focusedContainerColor = colorScheme.surfaceVariant,
            unfocusedContainerColor = colorScheme.surfaceVariant,
            disabledContainerColor = colorScheme.surfaceVariant,
            errorContainerColor = colorScheme.surfaceVariant,
            cursorColor = colorScheme.primary,
            errorCursorColor = colorScheme.error,
        )
    }
}

private val textFieldColorsFields: TextFieldColorsFields by lazy {
    val clz = TextFieldColors::class.java
    fun field(name: String): Field = clz.getDeclaredField(name).also { it.isAccessible = true }
    TextFieldColorsFields(
        focusedTextColor = field("focusedTextColor"),
        unfocusedTextColor = field("unfocusedTextColor"),
        disabledTextColor = field("disabledTextColor"),
        errorTextColor = field("errorTextColor"),
        focusedContainerColor = field("focusedContainerColor"),
        unfocusedContainerColor = field("unfocusedContainerColor"),
        disabledContainerColor = field("disabledContainerColor"),
        errorContainerColor = field("errorContainerColor"),
        cursorColor = field("cursorColor"),
        errorCursorColor = field("errorCursorColor"),
    )
}

private class TextFieldColorsFields(
    val focusedTextColor: Field,
    val unfocusedTextColor: Field,
    val disabledTextColor: Field,
    val errorTextColor: Field,
    val focusedContainerColor: Field,
    val unfocusedContainerColor: Field,
    val disabledContainerColor: Field,
    val errorContainerColor: Field,
    val cursorColor: Field,
    val errorCursorColor: Field,
)
