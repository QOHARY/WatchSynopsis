package com.example.uas.watchsynopsis.presentation.screens

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uas.watchsynopsis.R
import com.example.uas.watchsynopsis.presentation.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController) {
    val firebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("User action logged: SplashScreen.kt")
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        firebaseAnalytics.logEvent("splash_screen_view") {
            param("screen", "SplashScreen")
        }
        startAnimation = true
        delay(2000)
        navController.popBackStack()
        navController.navigate(Screen.SignInScreen.route)
        firebaseAnalytics.logEvent("navigate_to_sign_in") {
            param("screen", "SplashScreen")
        }
    }
    Splash(alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha),
            painter = painterResource(id = R.drawable.film_reel),
            contentDescription = "Logo Image"
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier
                .alpha(alpha),
            text = "Watch Synopsis",
            style = TextStyle(
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenDayPreview() {
    Splash(1f)
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SplashScreenNightPreview() {
    Splash(1f)
}