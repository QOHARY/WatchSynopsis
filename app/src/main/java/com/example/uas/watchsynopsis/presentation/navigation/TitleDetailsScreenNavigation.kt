package com.example.uas.watchsynopsis.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.uas.watchsynopsis.presentation.screens.TitleDetailsScreen
import com.example.uas.watchsynopsis.presentation.viewmodels.TitleDetailsViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

internal const val titleDetailsScreenRoute = "titleDetails"

fun NavGraphBuilder.titleDetailsScreen(
    navController: NavController,
    onPopBackStack: () -> Unit,
    onCastClick: (Int) -> Unit,
    onCrewClick: (Int) -> Unit
) {
    FirebaseCrashlytics.getInstance().log("User action logged: TitleDetailsScreenNavigation.kt")
    composable("$titleDetailsScreenRoute/{$titleIdArgument}") { backStackEntry ->

        backStackEntry.arguments?.getString(titleIdArgument)?.let { titleId ->
            val viewModel = viewModel<TitleDetailsViewModel>(
                factory = TitleDetailsViewModel.Factory
            )

            val state = viewModel.uiState.collectAsState().value

            TitleDetailsScreen(
                navController = navController,
                uiState = state,
                onCastClick = { onCastClick(titleId.toInt()) },
                onCrewClick = { onCrewClick(titleId.toInt()) }
            )

        } ?: LaunchedEffect(Unit) {
            onPopBackStack()
        }
    }
}

fun NavController.navigateToTitleDetails(
    titleId: Int,
    navOptions: NavOptions? = null
) {
    Firebase.analytics.logEvent("navigate_to_title_details") {
        param("title_id", titleId.toString())
    }
    navigate("$titleDetailsScreenRoute/$titleId", navOptions)
}
