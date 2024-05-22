package com.erbe.feature.indihome.ui.store.skeleton

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erbe.feature.indihome.component.IndihomeTheme
import com.erbe.feature.indihome.component.shimmer

@Composable
fun SkeletonItemProductLinear(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 8.dp, bottom = 8.dp)
            .border(
                2.dp,
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                RoundedCornerShape(8.dp)
            )
    ) {
        Row(modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer()
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .shimmer()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .shimmer()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.25f)
                        .height(12.dp)
                        .shimmer()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.25f)
                        .height(12.dp)
                        .shimmer()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSkeletonItemProductLinear() {
    IndihomeTheme {
        SkeletonItemProductLinear()
    }
}