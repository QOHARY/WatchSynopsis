package com.example.uas.watchsynopsis.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uas.watchsynopsis.R
import com.example.uas.watchsynopsis.presentation.theme.MyApplicationTheme
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.example.uas.watchsynopsis.presentation.viewmodels.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopProfileBar(
    preferenceHelper: PreferenceHelper,
    userViewModel: UserViewModel = hiltViewModel(),
    onSettingsClick: () -> Unit
) {
    val firebaseAnalytics = Firebase.analytics
    val userEmail = preferenceHelper.getUserLoggedIn()
    val userName by userViewModel.userName.collectAsState("")
    //Collect as name of User Login, and using it
    LaunchedEffect(userEmail) {
        userEmail?.let {
            userViewModel.loadUserName(it)
        }
    }
    //Topbar Navigation on App
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Logo and Username on the left
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.film_reel),
                        contentDescription = "Logo",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp)) // Space between logo and username
                    Text(
                        text = "Hello, $userName",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Watch Mode in the center
                Text(
                    text = "WatchSynopsis",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                // Settings Icon on the right
                IconButton(
                    onClick = {
                        FirebaseCrashlytics.getInstance().log("Settings button clicked in TopProfileBar")
                        firebaseAnalytics.logEvent("settings_button_click") {
                            param("screen", "TopProfileBar")
                        }
                        onSettingsClick()
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", tint = Color.Black)
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFE7F0F5))
    )
}

@Preview
@Composable
fun TopProfileBarPreview() {
    MyApplicationTheme {
        TopProfileBar(
            preferenceHelper = PreferenceHelper(LocalContext.current),
            onSettingsClick = { }
        )
    }
}
