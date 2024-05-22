package com.erbe.feature.indihome.di

import com.erbe.feature.indihome.common.di.dispatcherModule
import com.erbe.feature.indihome.data.di.dataModule
import com.erbe.feature.indihome.network.di.networkModule
import com.erbe.feature.indihome.ui.di.featureModule
import org.koin.dsl.koinApplication

val indihomeModule = listOf(dispatcherModule, networkModule, dataModule, featureModule)

object IsolatedContextIndihome {

    val koinApp = koinApplication {
        modules(indihomeModule)
    }
}