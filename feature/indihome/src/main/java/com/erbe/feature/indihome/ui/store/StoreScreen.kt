package com.erbe.feature.indihome.ui.store

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.erbe.feature.indihome.R
import com.erbe.feature.indihome.common.extension.UiState
import com.erbe.feature.indihome.common.extension.onError
import com.erbe.feature.indihome.common.extension.onLoading
import com.erbe.feature.indihome.common.extension.onSuccess
import com.erbe.feature.indihome.common.helper.thousandSeparator
import com.erbe.feature.indihome.component.IndihomeTheme
import com.erbe.feature.indihome.data.api.model.Product
import com.erbe.feature.indihome.data.api.model.dummyProduct
import com.erbe.feature.indihome.ui.store.filter.BottomSheetStoreScreen
import com.erbe.feature.indihome.ui.store.model.ProductParam
import com.erbe.feature.indihome.ui.store.search.DialogStoreScreen
import com.erbe.feature.indihome.ui.store.skeleton.SkeletonProductGrid
import com.erbe.feature.indihome.ui.store.skeleton.SkeletonProductLinear
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun StoreScreen(
    storeToProduct: (String) -> Unit,
    storeViewModel: StoreViewModel = koinViewModel()
) {
    val isLinear by storeViewModel.isLinear.collectAsStateWithLifecycle()
    val param by storeViewModel.param.collectAsStateWithLifecycle()
    val products by storeViewModel.products.collectAsStateWithLifecycle()

    StoreScreenContent(
        productsState = products,
        isLinear = isLinear,
        setLinear = storeViewModel::setLinear,
        param = param,
        setSearch = storeViewModel::setSearch,
        setQuery = { sort, category, lowest, highest ->
            storeViewModel.setQuery(category, lowest, highest, sort)
        },
        resetParam = storeViewModel::resetParam,
        onProduct = storeToProduct
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreenContent(
    productsState: UiState<List<Product>>,
    isLinear: Boolean,
    setLinear: () -> Unit,
    param: ProductParam,
    setSearch: (String?) -> Unit,
    setQuery: (String?, String?, Int?, Int?) -> Unit,
    resetParam: () -> Unit,
    onProduct: (String) -> Unit
) {
    // Dialog
    var openDialog by rememberSaveable { mutableStateOf(false) }

    if (openDialog) {
        Dialog(
            onDismissRequest = {
                openDialog = false
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                tonalElevation = 0.dp
            ) {
                DialogStoreScreen(
                    dismiss = { openDialog = false },
                    onSelectProduct = { query -> setSearch(query) },
                    defaultSearch = param.search ?: "",
                )
            }
        }
    }

    // BottomSheet
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            tonalElevation = 0.dp,
            dragHandle = {}
        ) {
            BottomSheetStoreScreen(
                dismiss = {
                    coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            openBottomSheet = false
                        }
                    }
                },
                onShowProduct = { sort, category, lowest, highest ->
                    setQuery(sort, category, lowest?.toIntOrNull(), highest?.toIntOrNull())
                },
                defaultSort = param.sort ?: "",
                defaultCategory = param.brand ?: "",
                defaultLowest = param.lowest?.toString() ?: "",
                defaultHighest = param.highest?.toString() ?: "",
            )
        }
    }

    // Content
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { openDialog = true },
                enabled = false,
                label = { Text(text = param.search ?: stringResource(R.string.text_hint_search)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            productsState
                .onLoading {
                    if (isLinear) {
                        SkeletonProductLinear(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 8.dp
                            )
                        )
                    } else {
                        SkeletonProductGrid(modifier = Modifier.padding(start = 16.dp, end = 8.dp))
                    }
                }
                .onError {
                    Spacer(modifier = Modifier.weight(1f))
                    /*ErrorScreen(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        error = error.handleNetworkError(),
                        isRefresh = true,
                        onRefresh = {},
                        isReset = true,
                        onReset = resetParam
                    )*/
                }
                .onSuccess { data ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AssistChip(
                            onClick = { openBottomSheet = true },
                            label = { Text(text = stringResource(R.string.chip_filter)) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_tune),
                                    contentDescription = null
                                )
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        val listQuery = listOf(
                            param.sort,
                            param.brand,
                            param.lowest?.let { "> ${it.thousandSeparator()}" },
                            param.highest?.let { "< ${it.thousandSeparator()}" },
                        )

                        LazyRow(modifier = Modifier.weight(1f)) {
                            listQuery.forEach { query ->
                                query?.let {
                                    item {
                                        InputChip(
                                            selected = false,
                                            onClick = {},
                                            label = { Text(text = query) },
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        VerticalDivider(modifier = Modifier.height(24.dp))

                        IconButton(onClick = setLinear) {
                            Icon(
                                painter = painterResource(
                                    if (isLinear) {
                                        R.drawable.ic_list_bulleted
                                    } else {
                                        R.drawable.ic_grid
                                    }
                                ),
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(if (isLinear) 1 else 2),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(start = 16.dp, end = 8.dp)
                    ) {
                        items(items = data, key = { it.productId }) { item ->
                            if (isLinear) {
                                ItemProductLinear(item) {
                                    onProduct(item.productId)
                                }
                            } else {
                                ItemProductGrid(item) {
                                    onProduct(item.productId)
                                }
                            }
                        }
                    }
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStoreScreen() {
    IndihomeTheme {
        StoreScreenContent(
            UiState.Success(listOf(dummyProduct)),
            true,
            {},
            ProductParam(),
            {},
            { _, _, _, _ -> },
            {},
            {}
        )
    }
}