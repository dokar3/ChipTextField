package com.dokar.chiptextfield

data class TextChip(override var text: String) : Chip

interface Chip {
    var text: String
}