package com.erbe.feature.indihome.ui.di

import com.erbe.feature.indihome.data.api.IndihomeRepository
import com.erbe.feature.indihome.ui.product.ProductViewModel
import com.erbe.feature.indihome.ui.store.StoreViewModel
import com.erbe.feature.indihome.ui.store.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {

    viewModel { StoreViewModel(get<IndihomeRepository>()) }
    viewModel { SearchViewModel(get<IndihomeRepository>()) }
    viewModel { ProductViewModel(get<IndihomeRepository>(), get()) }
}