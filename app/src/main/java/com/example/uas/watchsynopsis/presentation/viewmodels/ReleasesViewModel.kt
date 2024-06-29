package com.example.uas.watchsynopsis.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.watchsynopsis.data.api.Repository
import com.example.uas.watchsynopsis.data.api.firebaseAnalytics
import com.example.uas.watchsynopsis.presentation.uistate.ReleasesUiState
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReleasesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReleasesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {

            val repository = Repository()

            try {
                FirebaseCrashlytics.getInstance().log("Fetching releases")
                firebaseAnalytics.logEvent("fetch_releases") {
                    param("event", "fetch_releases")
                }
                val result = repository.getReleases()

                _uiState.update {
                    it.copy(releases = result)
                }

            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                e.printStackTrace()
            }
        }
    }
}