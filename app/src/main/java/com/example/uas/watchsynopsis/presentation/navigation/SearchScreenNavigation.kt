package com.example.uas.watchsynopsis.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.uas.watchsynopsis.domain.Title
import com.example.uas.watchsynopsis.presentation.screens.SearchScreen
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.example.uas.watchsynopsis.presentation.viewmodels.SearchViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

internal const val searchScreenRoute = "search"

fun NavGraphBuilder.searchScreen(navController: NavController,
                                 preferenceHelper: PreferenceHelper,
                                 onTitleClick: (Title) -> Unit) {
    FirebaseCrashlytics.getInstance().log("User action logged: SearchScreenNavigation.kt")
    composable(searchScreenRoute) {
        val viewModel: SearchViewModel = viewModel<SearchViewModel>()
        val state = viewModel.uiState.collectAsState().value

        SearchScreen(uiState = state,
            navController = navController,
            preferenceHelper = preferenceHelper,
            onTitleClick = onTitleClick)
    }
}

fun NavController.navigateToSearchScreen(
    navOptions: NavOptions? = null
) {
    Firebase.analytics.logEvent("navigate_to_search_screen") {
        param("screen", "SearchScreen")
    }
    navigate(searchScreenRoute, navOptions)
}
