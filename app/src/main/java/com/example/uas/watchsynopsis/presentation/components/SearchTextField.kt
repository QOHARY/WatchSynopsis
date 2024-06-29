package com.example.uas.watchsynopsis.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.uas.watchsynopsis.presentation.theme.MyApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    searchText: String,
    onSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val firebaseAnalytics = Firebase.analytics
    //Searching Movie
    OutlinedTextField(
        value = searchText,
        onValueChange = { newValue ->
            FirebaseCrashlytics.getInstance().log("Search text changed: $newValue")
            firebaseAnalytics.logEvent("search_text_change") {
                param("search_text", newValue)
                param("screen", "SearchTextField")
            }
            onSearchChange(newValue)
        },
        modifier,
        shape = RoundedCornerShape(100),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        label = {
            Text(text = "Movies and TV Shows")
        },
        placeholder = {
            Text("What are you looking for?")
        })
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    MyApplicationTheme {
        Surface {
            SearchTextField(
                "",
                onSearchChange = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldWithSearchTextPreview() {
    MyApplicationTheme {
        Surface {
            SearchTextField(
                searchText = "a",
                onSearchChange = {},
            )
        }
    }
}