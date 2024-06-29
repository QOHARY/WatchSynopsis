package com.example.uas.watchsynopsis.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas.watchsynopsis.data.api.Repository
import com.example.uas.watchsynopsis.data.api.firebaseAnalytics
import com.example.uas.watchsynopsis.presentation.navigation.titleIdArgument
import com.example.uas.watchsynopsis.presentation.uistate.TitleDetailsUiState
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TitleDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(TitleDetailsUiState())
    val uiState = _uiState.asStateFlow()
    private val repository = Repository()

    init {
        viewModelScope.launch {
            savedStateHandle
                .getStateFlow<String?>(titleIdArgument, null)
                .filterNotNull()
                .collect { id ->
                    findTitleById(id.toInt())
                }
        }
    }

    private fun findTitleById(titleId: Int) {
        viewModelScope.launch {
            try {
                FirebaseCrashlytics.getInstance().log("Fetching title details for titleId: $titleId")
                firebaseAnalytics.logEvent("fetch_title_details") {
                    param("title_id", titleId.toString())
                }
                val result = repository.getTitleDetails(titleId)

                _uiState.update {
                    it.copy(title = result)
                }

            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                e.printStackTrace()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TitleDetailsViewModel(
                    savedStateHandle = createSavedStateHandle()
                )
            }
        }
    }
}