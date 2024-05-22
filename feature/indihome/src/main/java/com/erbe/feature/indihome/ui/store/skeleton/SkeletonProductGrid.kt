package com.erbe.feature.indihome.ui.store.skeleton

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erbe.feature.indihome.component.IndihomeTheme

@Composable
fun SkeletonProductGrid(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        SkeletonProductFilter(modifier = Modifier.padding(top = 8.dp, end = 8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            SkeletonItemProductGrid(modifier = Modifier.weight(1f))
            SkeletonItemProductGrid(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSkeletonProductGrid() {
    IndihomeTheme {
        SkeletonProductGrid()
    }
}