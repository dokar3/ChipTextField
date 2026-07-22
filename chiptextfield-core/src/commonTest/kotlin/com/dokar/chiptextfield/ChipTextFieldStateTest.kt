package com.dokar.chiptextfield

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ChipTextFieldStateTest {
    @Test
    fun textFieldFocusReflectsUiFocus() {
        val state = ChipTextFieldState<Chip>()

        assertFalse(state.isTextFieldFocused)

        state.updateTextFieldFocus(true)
        assertTrue(state.isTextFieldFocused)

        state.updateTextFieldFocus(false)
        assertFalse(state.isTextFieldFocused)
    }

    @Test
    fun repeatedTextFieldFocusRequestsAreDistinct() {
        val state = ChipTextFieldState<Chip>()

        state.focusTextField()
        val firstRequest = state.textFieldFocusRequest
        assertFalse(state.isTextFieldFocused)

        state.focusTextField()
        val secondRequest = state.textFieldFocusRequest

        assertEquals(TextFieldFocusState.Focused, firstRequest.state)
        assertEquals(TextFieldFocusState.Focused, secondRequest.state)
        assertNotEquals(firstRequest, secondRequest)
    }
}
