package com.erbe.feature.indihome.data.di

import com.erbe.feature.indihome.data.api.IndihomeRepository
import com.erbe.feature.indihome.data.impl.IndihomeRepositoryImpl
import com.erbe.feature.indihome.network.service.IndihomeService
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.dsl.module

val dataModule = module {

    single<IndihomeRepository> {
        IndihomeRepositoryImpl(
            get<IndihomeService>(),
            get<CoroutineDispatcher>()
        )
    }
}