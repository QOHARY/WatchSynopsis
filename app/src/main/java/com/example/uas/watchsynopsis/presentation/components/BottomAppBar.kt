package com.example.uas.watchsynopsis.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.uas.watchsynopsis.presentation.theme.MyApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

sealed class BottomAppBarItem(
    val label: String,
    val icon: ImageVector
) {
    //Bottombbar App Navigation
    object Releases : BottomAppBarItem(
        label = "Home",
        icon = Icons.Filled.Home
    )

    object Search : BottomAppBarItem(
        label = "Search",
        icon = Icons.Filled.Search
    )

    object Account : BottomAppBarItem(
        label = "Account",
        icon = Icons.Outlined.AccountCircle
    )

}

val bottomAppBarItems = listOf(
    BottomAppBarItem.Releases,
    BottomAppBarItem.Search,
    BottomAppBarItem.Account
)

@Composable
fun BottomAppBar(
    item: BottomAppBarItem,
    modifier: Modifier = Modifier,
    items: List<BottomAppBarItem> = emptyList(),
    onItemChange: (BottomAppBarItem) -> Unit = {}
) {
    val firebaseAnalytics = Firebase.analytics
    NavigationBar(modifier) {
        items.forEach {
            val label = it.label
            val icon = it.icon
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = item.label == label,
                onClick = {
                    FirebaseCrashlytics.getInstance().log("Navigation item clicked: $label")
                    firebaseAnalytics.logEvent("navigation_item_click") {
                        param("item_label", label)
                        param("screen", "BottomAppBar")
                    }
                    onItemChange(it)
                }
            )
        }
    }
}

@Preview
@Composable
fun PanucciBottomAppBarPreview() {
    MyApplicationTheme {
        BottomAppBar(
            item = bottomAppBarItems.first(),
            items = bottomAppBarItems
        )
    }
}