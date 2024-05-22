package com.erbe.feature.indihome.ui.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erbe.feature.indihome.R
import com.erbe.feature.indihome.common.helper.thousandSeparator
import com.erbe.feature.indihome.component.IndihomeTheme
import com.erbe.feature.indihome.data.api.model.Product

@Composable
fun ItemProductGrid(
    product: Product,
    onItemClick: (Product) -> Unit
) {
    ElevatedCard(
        onClick = { onItemClick(product) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(186.dp),
                placeholder = painterResource(R.drawable.thumbnail),
                error = painterResource(R.drawable.thumbnail),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = product.productName,
                    fontSize = 12.sp,
                    maxLines = 2
                )

                Text(
                    text = stringResource(
                        R.string.format_price,
                        product.productPrice.thousandSeparator()
                    ),
                    modifier = Modifier.padding(top = 2.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier.padding(top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_account),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = product.store,
                        fontSize = 10.sp
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = stringResource(
                            R.string.format_rating_and_sale,
                            product.productRating,
                            product.sale
                        ),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemProductGrid() {
    IndihomeTheme {
        ItemProductGrid(
            Product(
                productId = "productId",
                productName = "Lenovo Legion 7 16 I7 11800 16GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
                productPrice = 23499000,
                image = "image",
                brand = "Lenovo",
                store = "LenovoStore",
                sale = 10,
                productRating = 5f
            )
        ) {}
    }
}