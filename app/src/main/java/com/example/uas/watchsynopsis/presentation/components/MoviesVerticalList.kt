package com.example.uas.watchsynopsis.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uas.watchsynopsis.R
import com.example.uas.watchsynopsis.domain.Title
import com.example.uas.watchsynopsis.domain.titlesSample
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Composable
fun MoviesVerticalList(titles: List<Title>,
                       onTitleClick: (Title) -> Unit) {

    //Showing poster movie result of searchfield
    val firebaseAnalytics = Firebase.analytics
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp),
        ) {
        items(titles.size) { i ->
            Column {
                Row(modifier = Modifier.clickable {
                    FirebaseCrashlytics.getInstance().log("Movie title clicked: ${titles[i].title}")
                    firebaseAnalytics.logEvent("movie_title_click") {
                        param("movie_title", titles[i].title)
                        param("movie_id", titles[i].id.toString())
                        param("screen", "MoviesVerticalList")
                    }
                    onTitleClick(titles[i])
                })
                 {
                    AsyncImage(
                        modifier = Modifier.width(50.dp),
                        model = titles[i].poster,
                        contentScale = ContentScale.FillWidth,
                        placeholder = painterResource(id = R.drawable.film_reel),
                        contentDescription = titles[i].title
                    )

                    Text(modifier = Modifier.padding(start = 4.dp), text = titles[i].title)
                }
                Box(modifier = Modifier.height(6.dp))
            }

        }
    }
}



@Preview
@Composable
private fun MoviesVerticalListPreview() {
    MoviesVerticalList(titles = titlesSample){}
}