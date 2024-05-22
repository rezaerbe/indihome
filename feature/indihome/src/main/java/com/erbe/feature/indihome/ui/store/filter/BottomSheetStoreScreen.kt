package com.erbe.feature.indihome.ui.store.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.InputChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erbe.feature.indihome.R
import com.erbe.feature.indihome.component.IndihomeTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomSheetStoreScreen(
    dismiss: () -> Unit,
    onShowProduct: (String?, String?, String?, String?) -> Unit,
    defaultSort: String,
    defaultCategory: String,
    defaultLowest: String,
    defaultHighest: String
) {
    val isReset = defaultSort.isNotEmpty() ||
        defaultCategory.isNotEmpty() ||
        defaultLowest.isNotEmpty() ||
        defaultHighest.isNotEmpty()
    val sort = listOf(
        stringResource(R.string.text_rating),
        stringResource(R.string.text_sale),
        stringResource(R.string.text_lowest_price),
        stringResource(R.string.text_highest_price)
    )
    val category = listOf(
        stringResource(R.string.text_apple),
        stringResource(R.string.text_asus),
        stringResource(R.string.text_dell),
        stringResource(R.string.text_lenovo)
    )
    var selectedSort by rememberSaveable { mutableStateOf(defaultSort) }
    var selectedCategory by rememberSaveable { mutableStateOf(defaultCategory) }
    var lowest by rememberSaveable { mutableStateOf(defaultLowest) }
    var highest by rememberSaveable { mutableStateOf(defaultHighest) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 48.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.text_filter),
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if (isReset) {
                TextButton(
                    onClick = {
                        selectedSort = ""
                        selectedCategory = ""
                        lowest = ""
                        highest = ""
                    },
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text(text = stringResource(R.string.button_reset))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.text_sort),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        FlowRow(
            Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.Start,
        ) {
            sort.forEach { item ->
                InputChip(
                    selected = item == selectedSort,
                    onClick = { selectedSort = item },
                    label = { Text(text = item) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.text_category),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        FlowRow(
            Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.Start,
        ) {
            category.forEach { item ->
                InputChip(
                    selected = item == selectedCategory,
                    onClick = { selectedCategory = item },
                    label = { Text(text = item) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.text_price),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = lowest,
                onValueChange = { lowest = it },
                modifier = Modifier.weight(1f),
                label = { Text(text = stringResource(R.string.text_hint_lowest)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(16.dp))

            OutlinedTextField(
                value = highest,
                onValueChange = { highest = it },
                modifier = Modifier.weight(1f),
                label = { Text(text = stringResource(R.string.text_hint_highest)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                dismiss()
                onShowProduct(
                    selectedSort.takeIf { it.isNotEmpty() },
                    selectedCategory.takeIf { it.isNotEmpty() },
                    lowest.takeIf { it.isNotEmpty() },
                    highest.takeIf { it.isNotEmpty() }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.button_show_product))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomSheetStoreScreen() {
    IndihomeTheme {
        BottomSheetStoreScreen({}, { _, _, _, _ -> }, "", "", "", "")
    }
}