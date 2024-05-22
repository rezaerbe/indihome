package com.erbe.feature.indihome.ui.product

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.erbe.feature.indihome.R
import com.erbe.feature.indihome.common.extension.UiState
import com.erbe.feature.indihome.common.extension.onError
import com.erbe.feature.indihome.common.extension.onLoading
import com.erbe.feature.indihome.common.extension.onSuccess
import com.erbe.feature.indihome.common.helper.thousandSeparator
import com.erbe.feature.indihome.component.IndihomeTheme
import com.erbe.feature.indihome.data.api.model.ProductDetail
import com.erbe.feature.indihome.data.api.model.dummyProductDetail
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductScreen(
    productId: String?,
    onNavigateUp: () -> Unit,
    productToReview: (String) -> Unit,
    productViewModel: ProductViewModel = koinViewModel()
) {
    productId?.let { productViewModel.setProductId(it) }
    val detailProductState by productViewModel.detailProduct.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ProductScreenContent(
        onNavigateUp = onNavigateUp,
        productToReview = productToReview,
        detailProductState = detailProductState,
        insertCart = {},
        wishlistExist = false,
        insertWishlist = {},
        deleteWishlist = {},
        onBuyNow = {},
        snackbarHostState = snackbarHostState
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun ProductScreenContent(
    onNavigateUp: () -> Unit,
    productToReview: (String) -> Unit,
    detailProductState: UiState<ProductDetail>,
    insertCart: (ProductDetail) -> Unit,
    wishlistExist: Boolean,
    insertWishlist: (ProductDetail) -> Unit,
    deleteWishlist: (ProductDetail) -> Unit,
    onBuyNow: (String) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    var selectedVariant by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.text_product_detail)) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateUp) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back),
                                contentDescription = null
                            )
                        }
                    }
                )

                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        },
        bottomBar = {
            detailProductState
                .onSuccess { data ->
                    Column {
                        HorizontalDivider(modifier = Modifier.fillMaxWidth())

                        Row(modifier = Modifier.padding(8.dp)) {
                            OutlinedButton(
                                onClick = {
                                    val product = data.updateVariant(selectedVariant)
                                    val jsonString = product.toString()
                                    onBuyNow(Uri.encode(jsonString))
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = stringResource(R.string.button_buy_now))
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(
                                onClick = {
                                    val product = data.updateVariant(selectedVariant)
                                    insertCart(product)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = stringResource(R.string.button_cart))
                            }
                        }
                    }
                }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        detailProductState
            .onLoading {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .padding(paddingValues)
                )
            }
            .onError {
                /*ErrorScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    error = error.handleNetworkError(),
                    isRefresh = true,
                    onRefresh = {}
                )*/
            }
            .onSuccess { data ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box {
                        val pagerState = rememberPagerState(pageCount = { data.image.size })

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) { page ->
                            ItemProduct(data.image[page])
                        }

                        if (data.image.size > 1) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 8.dp)
                            ) {
                                repeat(data.image.size) { iteration ->
                                    val color =
                                        if (pagerState.currentPage == iteration) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.outlineVariant
                                        }

                                    Box(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                            .size(8.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val variantPrice =
                            data.productVariant.getOrNull(selectedVariant)?.variantPrice ?: 0
                        val totalPrice = data.productPrice.plus(variantPrice)
                        Text(
                            text = stringResource(
                                R.string.format_price,
                                totalPrice.thousandSeparator()
                            ),
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        IconButton(
                            onClick = {
                                val shareIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Product : ${data.productName}\n" +
                                            "Price : ${data.productPrice.thousandSeparator()}\n" +
                                            "Link : http://indihome.erbe.com/product/${data.productId}"
                                    )
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(shareIntent, null))
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_share),
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        IconButton(
                            onClick = {
                                val product = data.updateVariant(selectedVariant)
                                if (wishlistExist) {
                                    deleteWishlist(product)
                                } else {
                                    insertWishlist(product)
                                }
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (wishlistExist) {
                                        R.drawable.ic_favorite
                                    } else {
                                        R.drawable.ic_favorite_border
                                    }
                                ),
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = data.productName,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .testTag("Product"),
                        fontSize = 14.sp
                    )

                    Row(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.format_sale, data.sale),
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Row(
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline,
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.format_rating,
                                    data.productRating,
                                    data.totalRating
                                ),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(R.string.text_choose_variant),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    FlowRow(
                        Modifier
                            .fillMaxWidth(1f)
                            .wrapContentHeight(align = Alignment.Top)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        data.productVariant.forEachIndexed { index, item ->
                            InputChip(
                                selected = index == selectedVariant,
                                onClick = { selectedVariant = index },
                                label = { Text(text = item.variantName) },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(R.string.text_product_description),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = data.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.text_user_review),
                            modifier = Modifier.weight(1f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        TextButton(
                            onClick = { productToReview(data.productId) },
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        ) {
                            Text(text = stringResource(R.string.button_see_all))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_star),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Row {
                            Text(
                                text = data.productRating.toString(),
                                modifier = Modifier.alignByBaseline(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = stringResource(R.string.text_rating_all),
                                modifier = Modifier.alignByBaseline(),
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(32.dp))

                        Column {
                            Text(
                                text = stringResource(
                                    R.string.format_satisfaction,
                                    data.totalSatisfaction
                                ),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = stringResource(
                                    R.string.format_rating_and_review,
                                    data.totalRating,
                                    data.totalReview
                                ),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
    }
}

private fun ProductDetail.updateVariant(selectedVariant: Int): ProductDetail {
    val variant = this.productVariant.getOrNull(selectedVariant)
    val listVariant = if (variant != null) listOf(variant) else listOf()
    return this.copy(productVariant = listVariant)
}

@Preview(showBackground = true)
@Composable
fun PreviewProductScreen() {
    IndihomeTheme {
        ProductScreenContent(
            {},
            {},
            UiState.Success(dummyProductDetail),
            {},
            false,
            {},
            {},
            {},
            SnackbarHostState()
        )
    }
}