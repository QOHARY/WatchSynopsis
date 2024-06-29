package com.example.uas.watchsynopsis.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uas.watchsynopsis.domain.Title
import com.example.uas.watchsynopsis.domain.titlesSample
import com.example.uas.watchsynopsis.presentation.components.BottomAppBar
import com.example.uas.watchsynopsis.presentation.components.BottomAppBarItem
import com.example.uas.watchsynopsis.presentation.components.MovieGrid
import com.example.uas.watchsynopsis.presentation.components.TopProfileBar
import com.example.uas.watchsynopsis.presentation.components.bottomAppBarItems
import com.example.uas.watchsynopsis.presentation.navigation.navigateSingleTopWithPopUpTo
import com.example.uas.watchsynopsis.presentation.uistate.ReleasesUiState
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.example.uas.watchsynopsis.presentation.viewmodels.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReleasesScreen(
    uiState: ReleasesUiState,
    navController: NavController,
    preferenceHelper: PreferenceHelper,
    userViewModel: UserViewModel = hiltViewModel(),
    onMovieClick: (Title) -> Unit = {}
) {
    val firebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("User action logged: ReleasesScreen.kt")
    val backStackEntryState by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntryState?.destination
    val currentRoute = currentDestination?.route
    val selectedItem by remember(currentDestination) {
        val item = when (currentRoute) {
            "releases" -> BottomAppBarItem.Releases
            "search" -> BottomAppBarItem.Search
            else -> BottomAppBarItem.Releases
        }
        mutableStateOf(item)
    }

    Scaffold(
        topBar = {
            TopProfileBar(
                preferenceHelper = preferenceHelper,
                userViewModel = userViewModel,
                onSettingsClick = {
                    firebaseAnalytics.logEvent("settings_click") {
                        param("screen", "ReleasesScreen")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                item = selectedItem,
                items = bottomAppBarItems,
                onItemChange = { item ->
                    navController.navigateSingleTopWithPopUpTo(item)
                    firebaseAnalytics.logEvent("bottom_bar_item_click") {
                        param("item", item.label)
                        param("screen", "ReleasesScreen")
                    }
                },
            )
        },
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            MovieGrid(
                uiState.releases,
                onMovieClick = { title ->
                    onMovieClick(title)
                    // Log event when a movie is clicked
                    firebaseAnalytics.logEvent("movie_click") {
                        param("movie_title", title.title)
                        param("screen", "ReleasesScreen")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReleasesScreenPreview() {
    ReleasesScreen(
        uiState = ReleasesUiState(titlesSample),
        navController = rememberNavController(),
        preferenceHelper = PreferenceHelper(context = LocalContext.current)
    )
}