package com.erbe.feature.indihome.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.erbe.feature.indihome.ui.product.ProductScreen
import com.erbe.feature.indihome.ui.store.StoreScreen

private const val INDIHOME = "indihome"
private const val STORE = "store"
private const val PRODUCT = "product"
private const val PRODUCT_ID = "productId"

@Composable
fun IndihomeNavigation(
    navController: NavHostController,
    data: Uri?
) {
    val destination = data.toString()
    val path = data?.lastPathSegment

    val startDestination = with(destination) {
        when {
            contains(STORE) -> STORE
            contains(PRODUCT) -> "$PRODUCT/$path"
            else -> STORE
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        route = INDIHOME
    ) {
        composable(route = STORE) {
            StoreScreen(
                storeToProduct = { productId ->
                    navController.navigate("$PRODUCT/$productId")
                }
            )
        }
        composable(
            route = "$PRODUCT/{$PRODUCT_ID}",
            arguments = listOf(navArgument(PRODUCT_ID) { type = NavType.StringType })
        ) {
            ProductScreen(
                productId = path,
                onNavigateUp = {
                    navController.navigateUp()
                },
                productToReview = {}
            )
        }
    }
}