package com.example.uas.watchsynopsis.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.uas.watchsynopsis.presentation.screens.CastCrewDetailScreen
import com.example.uas.watchsynopsis.presentation.viewmodels.CastCrewViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

internal const val castCrewScreenRoute = "castCrew"
internal const val titleIdArgument = "titleId"
internal const val isCastArgument = "isCast"

fun NavGraphBuilder.castCrewScreen(
    navController: NavController,
    onPopBackStack: () -> Unit
) {
    FirebaseCrashlytics.getInstance().log("User action logged: CastCrewDetailScreenNavigation.kt")
    composable("$castCrewScreenRoute/{$titleIdArgument}/{$isCastArgument}") { backStackEntry ->
        val titleId = backStackEntry.arguments?.getString(titleIdArgument)
        val isCast = backStackEntry.arguments?.getString(isCastArgument)?.toBoolean() ?: true
        titleId?.let {
            val viewModel = viewModel<CastCrewViewModel>(
                factory = CastCrewViewModel.Factory
            )

            val state = viewModel.uiState.collectAsState().value
            CastCrewDetailScreen(
                navController,
                uiState = state,
                isCast = isCast,

            )
        } ?: LaunchedEffect(Unit) {
            onPopBackStack()
        }
    }
}

fun NavController.navigateToCastCrew(
    titleId: Int,
    isCast: Boolean,
    navOptions: NavOptions? = null
) {
    Firebase.analytics.logEvent("navigate_to_cast_crew") {
        param("title_id", titleId.toString())
        param("is_cast", isCast.toString())
    }
    val route = "$castCrewScreenRoute/$titleId/$isCast"
    navigate(route, navOptions)
}
