package com.dokar.chiptextfield.sample.m3

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun ChipsHeader(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        modifier = modifier.padding(8.dp, 4.dp),
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
    )
}