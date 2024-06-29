@file:Suppress("DEPRECATION")

package com.example.uas.watchsynopsis.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uas.watchsynopsis.presentation.components.BottomAppBar
import com.example.uas.watchsynopsis.presentation.components.BottomAppBarItem
import com.example.uas.watchsynopsis.presentation.components.TopProfileBar
import com.example.uas.watchsynopsis.presentation.components.bottomAppBarItems
import com.example.uas.watchsynopsis.presentation.navigation.Screen
import com.example.uas.watchsynopsis.presentation.navigation.navigateSingleTopWithPopUpTo
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.example.uas.watchsynopsis.presentation.viewmodels.UserViewModel
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavController,
    preferenceHelper: PreferenceHelper,
    userViewModel: UserViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val firebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("User action logged: AccountScreen.kt")
    val userEmail = preferenceHelper.getUserLoggedIn()
    val userName by userViewModel.userName.collectAsState("")

    LaunchedEffect(userEmail) {
        userEmail?.let {
            userViewModel.loadUserName(it)
        }
    }

    Scaffold(
        topBar = {
            TopProfileBar(
                preferenceHelper = preferenceHelper,
                userViewModel = userViewModel,
                onSettingsClick = {
                    firebaseAnalytics.logEvent("settings_click") {
                        param("screen", "AccountScreen")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                item = BottomAppBarItem.Account,
                items = bottomAppBarItems,
                onItemChange = { item ->
                    navController.navigateSingleTopWithPopUpTo(item)
                    firebaseAnalytics.logEvent("bottom_bar_item_click") {
                        param("item", item.label)
                        param("screen", "AccountScreen")
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Hello, $userName")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Are you sure want to log out from My App???")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    userViewModel.logout()
                    onLogout()
                    firebaseAnalytics.logEvent("logout_click") {
                        param("screen", "SignIn")
                    }
                    navController.navigate(Screen.SignInScreen.route) {
                        popUpTo(0) // or popUpTo(navController.graph.startDestinationId)
                        { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "Logout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    AccountScreen(
        navController = rememberNavController(),
        preferenceHelper = PreferenceHelper(LocalContext.current),
        onLogout = {}
    )
}
