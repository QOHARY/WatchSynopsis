package com.example.uas.watchsynopsis.presentation.uistate

import com.example.uas.watchsynopsis.domain.Title
import com.google.firebase.crashlytics.FirebaseCrashlytics

data class ReleasesUiState(
    val releases: List<Title> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null

)
