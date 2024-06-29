package com.example.uas.watchsynopsis.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.uas.watchsynopsis.R
import com.example.uas.watchsynopsis.domain.Title
import com.example.uas.watchsynopsis.domain.titlesSample
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    title: Title,
    onMovieClick: (Title) -> Unit
) {
    val firebaseAnalytics = Firebase.analytics
    //Poster Movie
    AsyncImage(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                FirebaseCrashlytics.getInstance().log("Movie poster clicked: ${title.title}")
                firebaseAnalytics.logEvent("movie_poster_click") {
                    param("movie_title", title.title)
                    param("movie_id", title.id.toString())
                    param("screen", "MoviePoster")
                }
                onMovieClick(title)
            },
        model = title.poster,
        contentScale = ContentScale.FillWidth,
        error = painterResource( R.drawable.film_reel),
        placeholder = painterResource(id = R.drawable.film_reel),
        contentDescription = title.title
    )
}

@Preview
@Composable
private fun MoviePosterPreview() {
    MoviePoster(
        modifier = Modifier,
        title = titlesSample.first()
    ) {}
}