package com.example.uas.watchsynopsis.presentation.uistate

import com.example.uas.watchsynopsis.domain.Cast
import com.example.uas.watchsynopsis.domain.Crew
import com.google.firebase.crashlytics.FirebaseCrashlytics

data class CastCrewUiState(
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
