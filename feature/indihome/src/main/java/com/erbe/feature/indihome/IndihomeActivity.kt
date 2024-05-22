package com.erbe.feature.indihome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.erbe.feature.indihome.component.IndihomeTheme
import com.erbe.feature.indihome.di.IsolatedContextIndihome
import com.erbe.feature.indihome.navigation.IndihomeNavigation
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinIsolatedContext

class IndihomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IndihomeTheme {
                val navController = rememberNavController()
                val data = intent.data

                IsolatedContextIndihome.koinApp.androidContext(this@IndihomeActivity)
                KoinIsolatedContext(context = IsolatedContextIndihome.koinApp) {
                    IndihomeNavigation(navController, data)
                }
            }
        }
    }
}