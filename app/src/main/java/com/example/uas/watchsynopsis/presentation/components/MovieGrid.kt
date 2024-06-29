package com.example.uas.watchsynopsis.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uas.watchsynopsis.domain.Title
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Composable
fun MovieGrid(
    titles: List<Title>,
    onMovieClick: (Title) -> Unit = {}
) {
    val firebaseAnalytics = Firebase.analytics
    //Poster Movie in Grid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),

        ) {
        items(titles.size) { index ->
            MoviePoster(
                modifier = Modifier.height(300.dp).width(150.dp),
                title = titles[index],
                onMovieClick = {
                    FirebaseCrashlytics.getInstance().log("Movie clicked: ${titles[index].title}")
                    firebaseAnalytics.logEvent("movie_poster_click") {
                        param("movie_title", titles[index].title)
                        param("movie_id", titles[index].id.toString())
                        param("screen", "MovieGrid")
                    }
                    onMovieClick(titles[index])
                }
            )
        }
    }
}