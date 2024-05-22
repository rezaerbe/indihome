package com.erbe.feature.indihome.ui.store.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.erbe.feature.indihome.R
import com.erbe.feature.indihome.common.extension.UiState
import com.erbe.feature.indihome.common.extension.onInit
import com.erbe.feature.indihome.common.extension.onLoading
import com.erbe.feature.indihome.common.extension.onSuccess
import com.erbe.feature.indihome.component.IndihomeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DialogStoreScreen(
    dismiss: () -> Unit,
    onSelectProduct: (String?) -> Unit,
    defaultSearch: String,
    searchViewModel: SearchViewModel = koinViewModel()
) {
    val searchState by searchViewModel.search.collectAsStateWithLifecycle()

    DialogStoreScreenContent(
        dismiss = dismiss,
        onSelectProduct = onSelectProduct,
        defaultSearch = defaultSearch,
        onSearch = searchViewModel::getSearch,
        onReset = searchViewModel::reset,
        searchState = searchState
    )
}

@Composable
fun DialogStoreScreenContent(
    dismiss: () -> Unit,
    onSelectProduct: (String?) -> Unit,
    defaultSearch: String,
    onSearch: (String) -> Unit,
    onReset: () -> Unit,
    searchState: UiState<List<String>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var search by rememberSaveable { mutableStateOf(defaultSearch) }
        val focusRequester = remember { FocusRequester() }

        OutlinedTextField(
            value = search,
            onValueChange = {
                search = it
                onSearch(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .testTag(stringResource(R.string.text_hint_search)),
            placeholder = { Text(text = stringResource(R.string.text_hint_search)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (search.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.ic_clear),
                        contentDescription = null,
                        modifier = Modifier.clickable { search = "" }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onReset()
                dismiss()
                onSelectProduct(search.takeIf { it.isNotEmpty() })
            }),
            singleLine = true
        )

        val windowInfo = LocalWindowInfo.current

        LaunchedEffect(Unit) {
            snapshotFlow { windowInfo.isWindowFocused }.collect { isWindowFocused ->
                if (isWindowFocused) {
                    focusRequester.requestFocus()
                }
            }
        }

        BackHandler {
            onReset()
            dismiss()
        }

        searchState
            .onInit {
                if (defaultSearch.isNotEmpty()) {
                    onSearch(defaultSearch)
                }
            }
            .onLoading {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(32.dp)
                )
            }
            .onSuccess { data ->
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(data) { item ->
                        ItemSearch(item) {
                            onReset()
                            dismiss()
                            onSelectProduct(item.takeIf { it.isNotEmpty() })
                        }
                    }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialogStoreScreen() {
    IndihomeTheme {
        DialogStoreScreenContent({}, {}, "", {}, {}, UiState.Success(listOf("Lenovo")))
    }
}