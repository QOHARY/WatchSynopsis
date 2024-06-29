package com.example.uas.watchsynopsis.presentation.uistate

import com.example.uas.watchsynopsis.domain.Cast
import com.example.uas.watchsynopsis.domain.Crew
import com.example.uas.watchsynopsis.domain.Title
import com.google.firebase.crashlytics.FirebaseCrashlytics

data class TitleDetailsUiState(
    val title: Title? = null,
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null

)