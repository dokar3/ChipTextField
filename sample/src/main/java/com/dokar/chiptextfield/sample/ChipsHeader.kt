package com.dokar.chiptextfield.sample

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun ChipsHeader(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        modifier = modifier.padding(8.dp, 4.dp),
        fontSize = 15.sp,
        color = MaterialTheme.colors.onSecondary
    )
}