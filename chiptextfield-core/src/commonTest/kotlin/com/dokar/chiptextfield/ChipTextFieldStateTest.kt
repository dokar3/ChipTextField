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

    @Test
    fun consumedTextFieldFocusRequestBecomesInert() {
        val state = ChipTextFieldState<Chip>()
        state.focusTextField()
        val request = state.textFieldFocusRequest

        state.consumeTextFieldFocusRequest(request)

        assertEquals(TextFieldFocusState.None, state.textFieldFocusRequest.state)
    }

    @Test
    fun consumingOldTextFieldFocusRequestDoesNotClearNewRequest() {
        val state = ChipTextFieldState<Chip>()
        state.focusTextField()
        val oldRequest = state.textFieldFocusRequest
        state.clearTextFieldFocus()
        val newRequest = state.textFieldFocusRequest

        state.consumeTextFieldFocusRequest(oldRequest)

        assertEquals(newRequest, state.textFieldFocusRequest)
        assertEquals(TextFieldFocusState.Unfocused, state.textFieldFocusRequest.state)
    }

    @Test
    fun focusingChipCancelsPendingTextFieldFocusRequest() {
        val chip = Chip("Chip")
        val state = ChipTextFieldState(chips = listOf(chip))
        state.focusTextField()
        val pendingRequest = state.textFieldFocusRequest

        state.focusChip(0)

        assertEquals(pendingRequest.id, state.textFieldFocusRequest.id)
        assertEquals(TextFieldFocusState.None, state.textFieldFocusRequest.state)
        assertEquals(0, state.focusedChipIndex)
    }
}
