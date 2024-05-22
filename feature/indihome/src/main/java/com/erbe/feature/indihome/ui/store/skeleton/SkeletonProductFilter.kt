package com.erbe.feature.indihome.ui.store.skeleton

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erbe.feature.indihome.component.IndihomeTheme
import com.erbe.feature.indihome.component.shimmer

@Composable
fun SkeletonProductFilter(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .width(84.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmer()
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmer()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSkeletonProductFilter() {
    IndihomeTheme {
        SkeletonProductFilter()
    }
}