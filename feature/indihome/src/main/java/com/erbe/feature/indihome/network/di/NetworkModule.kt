package com.erbe.feature.indihome.network.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.erbe.feature.indihome.network.service.IndihomeService
import com.erbe.feature.indihome.network.util.BASE_URL
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single {
        ChuckerCollector(androidContext(), true, RetentionManager.Period.ONE_HOUR)
    }

    single {
        ChuckerInterceptor.Builder(androidContext())
            .collector(get<ChuckerCollector>())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<ChuckerInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(IndihomeService::class.java)
    }
}