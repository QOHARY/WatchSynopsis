package com.example.uas.watchsynopsis.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uas.watchsynopsis.presentation.components.PersonGrid
import com.example.uas.watchsynopsis.presentation.uistate.CastCrewUiState
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase

@Composable
fun CastCrewDetailScreen(
    navController: NavController,
    uiState: CastCrewUiState,
    isCast: Boolean
) {
    val firebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("User action logged: CastCrewDetailScreen.kt")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = if (isCast) "Cast" else "Crew",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isCast) {
            PersonGrid(
                persons = uiState.cast
            )
            firebaseAnalytics.logEvent("view_cast_details") {
                param("screen", "CastCrewDetailScreen")
            }
        } else {
            PersonGrid(
                persons = uiState.crew
            )
            firebaseAnalytics.logEvent("view_crew_details") {
                param("screen", "CastCrewDetailScreen")
            }
        }
    }
}
