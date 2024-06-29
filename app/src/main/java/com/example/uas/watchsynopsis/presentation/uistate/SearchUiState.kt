package com.example.uas.watchsynopsis.presentation.uistate

import com.example.uas.watchsynopsis.domain.Title
import com.google.firebase.crashlytics.FirebaseCrashlytics

data class SearchUiState(
    val result: List<Title> = emptyList(),
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {},
    val isLoading: Boolean = false,
    val error: String? = null

)