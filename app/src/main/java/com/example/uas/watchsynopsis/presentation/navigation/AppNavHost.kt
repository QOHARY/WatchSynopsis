package com.example.uas.watchsynopsis.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.uas.watchsynopsis.data.api.firebaseAnalytics
import com.example.uas.watchsynopsis.presentation.components.BottomAppBarItem
import com.example.uas.watchsynopsis.presentation.screens.*
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = "splashScreen")
    object SignInScreen : Screen(route = "signInScreen")
    object SignUpScreen : Screen(route = "signUpScreen")
}

@Composable
fun AppNavHost(navController: NavHostController, preferenceHelper: PreferenceHelper) {
    val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("User action logged: AppNavHost.kt")

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            firebaseAnalytics.logEvent("navigate_to_splash_screen") {
                param("screen", "SplashScreen")
            }
            AnimatedSplashScreen(navController)
        }
        composable(Screen.SignInScreen.route) {
            firebaseAnalytics.logEvent("navigate_to_sign_in_screen") {
                param("screen", "SignInScreen")
            }
            SignInScreen(navController = navController, preferenceHelper = preferenceHelper)
        }
        composable(Screen.SignUpScreen.route) {
            firebaseAnalytics.logEvent("navigate_to_sign_up_screen") {
                param("screen", "SignUpScreen")
            }
            SignUpScreen(navController = navController, preferenceHelper = preferenceHelper)
        }

        releasesListScreen(
            navController = navController,
            preferenceHelper = preferenceHelper,
            onMovieClick = {
                firebaseAnalytics.logEvent("navigate_to_title_details") {
                    param("title_id", it.id.toString())
                }
                navController.navigateToTitleDetails(it.id)
            }
        )

        searchScreen(navController = navController, preferenceHelper = preferenceHelper,
            onTitleClick = {
                firebaseAnalytics.logEvent("navigate_to_title_details") {
                    param("title_id", it.id.toString())
                }
                navController.navigateToTitleDetails(it.id)
        }
        )
        castCrewScreen(
            navController,
            onPopBackStack = {
                navController.popBackStack()
            }
        )

        titleDetailsScreen(
            navController = navController,
            onPopBackStack = {
                navController.popBackStack()
            },
            onCastClick = { titleId ->
                firebaseAnalytics.logEvent("navigate_to_cast_crew") {
                    param("title_id", titleId.toString())
                    param("is_cast", "true")
                }
                navController.navigateToCastCrew(titleId, isCast = true)
            },
            onCrewClick = { titleId ->
                firebaseAnalytics.logEvent("navigate_to_cast_crew") {
                    param("title_id", titleId.toString())
                    param("is_cast", "false")
                }
                navController.navigateToCastCrew(titleId, isCast = false)
            }
        )

        accountScreen(
            navController = navController,
            preferenceHelper = preferenceHelper,
            onLogout = {
                firebaseAnalytics.logEvent("user_logged_out") {
                    param("user_email", preferenceHelper.getUserLoggedIn() ?: "")
                }
                preferenceHelper.clearUserLoggedIn()
                navController.navigate(Screen.SignInScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id){
                        inclusive = true  }
                }
            }
        )
    }
}

fun NavController.navigateSingleTopWithPopUpTo(
    item: BottomAppBarItem
) {
    val (route, navigate) = when (item) {
        BottomAppBarItem.Releases -> Pair(
            releasesListNavigationRoutes,
            ::navigateToReleasesList
        )
        BottomAppBarItem.Search -> Pair(
            searchScreenRoute,
            ::navigateToSearchScreen
        )
        BottomAppBarItem.Account -> Pair(
            accountScreenRoute,
            ::navigateToAccountScreen
        )
    }

    val navOptions = navOptions {
        launchSingleTop = true
        popUpTo(route) {
            inclusive = true
        }
    }
    firebaseAnalytics.logEvent("navigate_to_screen") {
        param("screen", route)
    }
    navigate(navOptions)
}
