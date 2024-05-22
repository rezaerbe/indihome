package com.erbe.feature.indihome.ui.product

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.erbe.feature.indihome.R
import com.erbe.feature.indihome.component.IndihomeTheme

@Composable
fun ItemProduct(image: String) {
    AsyncImage(
        model = image,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        placeholder = painterResource(R.drawable.thumbnail),
        error = painterResource(R.drawable.thumbnail)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewItemProduct() {
    IndihomeTheme {
        ItemProduct("")
    }
}