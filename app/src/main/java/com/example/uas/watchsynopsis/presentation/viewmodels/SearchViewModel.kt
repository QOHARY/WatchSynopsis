package com.example.uas.watchsynopsis.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.watchsynopsis.data.api.Repository
import com.example.uas.watchsynopsis.data.api.firebaseAnalytics
import com.example.uas.watchsynopsis.presentation.uistate.SearchUiState
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = Repository()
    private val _uiState = MutableStateFlow(SearchUiState())
    private var searchJob: Job? = null
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onSearchChange = {
                    _uiState.value = _uiState.value.copy(
                        searchText = it
                    )

                    search(it)
                }
            )
        }
    }

    private fun search(search: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            try {
                FirebaseCrashlytics.getInstance().log("Search initiated: $search")
                _uiState.update { currentState ->
                    currentState.copy(
                        result = repository.getSearch(search)
                    )
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                firebaseAnalytics.logEvent("search_initiated") {
                    param("search_query", search)
                }
                e.printStackTrace()

                _uiState.update { currentState ->
                    currentState.copy(
                        result = listOf()
                    )
                }
            }
        }
    }
}
