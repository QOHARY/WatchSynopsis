package com.example.uas.watchsynopsis.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.uas.watchsynopsis.R
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Composable
fun PersonItem(
    modifier: Modifier = Modifier,
    name: String,
    role: String,
    imageUrl: String?,
    onPersonClick: () -> Unit
) {
    val firebaseAnalytics = Firebase.analytics
    //Showing Profile Photo Person Cast and Crew
    Column(
        modifier = modifier
            .width(150.dp)
            .clickable {
                FirebaseCrashlytics.getInstance().log("Person item clicked: $name")
                firebaseAnalytics.logEvent("person_item_click") {
                    param("person_name", name)
                    param("person_role", role)
                    param("screen", "PersonItem")
                }
                onPersonClick() }
            .padding(8.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.film_reel),
            placeholder = painterResource(R.drawable.film_reel),
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1
        )
        Text(
            text = role,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun PersonItemPreview() {
    PersonItem(
        name = "John Doe",
        role = "Actor",
        imageUrl = null
    ) {}
}
