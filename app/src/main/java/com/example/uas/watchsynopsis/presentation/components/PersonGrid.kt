package com.example.uas.watchsynopsis.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uas.watchsynopsis.domain.Cast
import com.example.uas.watchsynopsis.domain.Crew
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Composable
fun PersonGrid(
    persons: List<Any>,
    onPersonClick: (Int) -> Unit = {}
) {
    val firebaseAnalytics = Firebase.analytics
    //Showing Person Cast and Crew in Grid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(persons) { person ->
            when (person) {
                is Cast -> {
                    PersonItem(
                        name = person.name,
                        role = person.role,
                        imageUrl = person.headshotUrl,
                        onPersonClick = {
                            FirebaseCrashlytics.getInstance().log("Person clicked: ${person.name}")
                            firebaseAnalytics.logEvent("person_click") {
                                param("person_name", person.name)
                                param("person_id", person.id.toString())
                                param("person_role", "cast")
                                param("screen", "PersonGrid")
                            }
                            onPersonClick(person.id) }
                    )
                }
                is Crew -> {
                    PersonItem(
                        name = person.name,
                        role = person.role,
                        imageUrl = person.headshotUrl,
                        onPersonClick = {
                            FirebaseCrashlytics.getInstance().log("Person clicked: ${person.name}")
                            firebaseAnalytics.logEvent("person_click") {
                                param("person_name", person.name)
                                param("person_id", person.id.toString())
                                param("person_role", "crew")
                                param("screen", "PersonGrid")
                            }
                            onPersonClick(person.id) }
                    )
                }
            }
        }
    }
}
