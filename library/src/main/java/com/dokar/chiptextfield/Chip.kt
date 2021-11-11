package com.dokar.chiptextfield

data class TextChip(override var value: String) : Chip

interface Chip {
    var value: String

    companion object {
        fun textChip(text: String) = TextChip(text)
    }
}