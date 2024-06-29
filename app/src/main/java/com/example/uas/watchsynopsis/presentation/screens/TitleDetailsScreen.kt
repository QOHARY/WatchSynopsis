package com.example.uas.watchsynopsis.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.uas.watchsynopsis.presentation.components.TrailerPlayer
import com.example.uas.watchsynopsis.presentation.uistate.TitleDetailsUiState
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase

@Composable
fun TitleDetailsScreen(
    navController: NavController,
    uiState: TitleDetailsUiState,
    onCastClick: (Int) -> Unit,
    onCrewClick: (Int) -> Unit
) {
    val firebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("User action logged: TitleDetailsScreen.kt")
    uiState.title?.let { title ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Image(
                painter = rememberAsyncImagePainter(title.poster),
                contentDescription = title.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            firebaseAnalytics.logEvent("view_title_details") {
                param("title_id", title.id.toString())
                param("title_name", title.title)
                param("screen", "TitleDetailsScreen")
            }

            Text(
                text = "${title.title} (${title.year})",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 24.sp
            )

            Text(
                text = title.plot ?: "",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Text(
                text = "Genres: " + title.genreNames?.joinToString(", "),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            title.userRating?.let {
                Text(
                    text = "Users Rating: $it",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }

            title.criticScore?.let {
                Text(
                    text = "Critics Rating: $it",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }

            title.trailer?.let { trailerUrl ->
                TrailerPlayer(trailerUrl = trailerUrl)
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    FirebaseCrashlytics.getInstance().log("Cast button clicked: ${title.id}")
                    firebaseAnalytics.logEvent("cast_button_click") {
                        param("title_id", title.id.toString())
                        param("title_name", title.title)
                        param("screen", "TitleDetailsScreen")
                    }
                    onCastClick(title.id)
                }) {
                    Text(text = "Cast")
                }
                Button(onClick = {
                    FirebaseCrashlytics.getInstance().log("Crew button clicked: ${title.id}")
                    firebaseAnalytics.logEvent("crew_button_click") {
                        param("title_id", title.id.toString())
                        param("title_name", title.title)
                        param("screen", "TitleDetailsScreen")
                    }
                    onCrewClick(title.id)
                }) {
                    Text(text = "Crew")
                }
            }
        }
    }
}

