package com.example.uas.watchsynopsis.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.uas.watchsynopsis.domain.Title
import com.example.uas.watchsynopsis.presentation.components.BottomAppBar
import com.example.uas.watchsynopsis.presentation.components.BottomAppBarItem
import com.example.uas.watchsynopsis.presentation.components.bottomAppBarItems
import com.example.uas.watchsynopsis.presentation.components.MoviesVerticalList
import com.example.uas.watchsynopsis.presentation.components.SearchTextField
import com.example.uas.watchsynopsis.presentation.components.TopProfileBar
import com.example.uas.watchsynopsis.presentation.uistate.SearchUiState
import com.example.uas.watchsynopsis.presentation.navigation.navigateSingleTopWithPopUpTo
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.example.uas.watchsynopsis.presentation.viewmodels.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    navController: NavController,
    preferenceHelper: PreferenceHelper,
    userViewModel: UserViewModel = hiltViewModel(),
    onTitleClick: (Title) -> Unit,
) {
    val firebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("User action logged: SearchScreen.kt")
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
            TopProfileBar( preferenceHelper = preferenceHelper,
                userViewModel = userViewModel,
                onSettingsClick = {
                    firebaseAnalytics.logEvent("settings_click") {
                        param("screen", "SearchScreen")
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
                        param("screen", "SearchScreen")
                    }
                },
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            SearchTextField(
                searchText = uiState.searchText,
                onSearchChange = { text ->
                    uiState.onSearchChange(text)
                    firebaseAnalytics.logEvent("search_text_change") {
                        param("search_text", text)
                        param("screen", "SearchScreen")
                    }
                },
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            )

            MoviesVerticalList(titles = uiState.result, onTitleClick = { title ->
                onTitleClick(title)
                firebaseAnalytics.logEvent("movie_click") {
                    param("movie_title", title.title)
                    param("screen", "SearchScreen")
                }
            })
        }
    }
}
