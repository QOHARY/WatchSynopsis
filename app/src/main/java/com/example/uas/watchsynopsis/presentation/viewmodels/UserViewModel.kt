package com.example.uas.watchsynopsis.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.watchsynopsis.data.local.Repository
import com.example.uas.watchsynopsis.data.local.User
import com.example.uas.watchsynopsis.data.api.firebaseAnalytics
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository,
    val preferenceHelper: PreferenceHelper
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> get() = _userName

    fun insertUser(user: User) {
        viewModelScope.launch(IO) {
            FirebaseCrashlytics.getInstance().log("Inserting user: ${user.userName}")
            firebaseAnalytics.logEvent("insert_user") {
                param("user_name", user.userName)
                param("user_email", user.email)
            }
            repository.insert(user)
        }
    }

    suspend fun authenticateUser(email: String, password: String): Boolean {
        val user = repository.getUserByEmailAndPassword(email, password)
        if (user != null) {
            _userName.value = user.userName
            FirebaseCrashlytics.getInstance().log("User authenticated: $email")
            firebaseAnalytics.setUserId(user.id.toString())
            firebaseAnalytics.logEvent("user_authenticated") {
                param("user_email", email)
            }
            return true
        }
        FirebaseCrashlytics.getInstance().log("Authentication failed for email: $email")
        firebaseAnalytics.logEvent("authentication_failed") {
            param("user_email", email)
        }
        return false
    }

    fun loadUserName(email: String) {
        viewModelScope.launch {
            val name = repository.getUserNameByEmail(email)
            if (name != null) {
                _userName.value = name
                FirebaseCrashlytics.getInstance().log("Loaded user name for email: $email")
                firebaseAnalytics.logEvent("load_user_name") {
                    param("user_email", email)
                    param("user_name", name)
                }
            }
        }
    }
    fun logout() {
        FirebaseCrashlytics.getInstance().log("User logged out")
        firebaseAnalytics.logEvent("user_logged_out") {
            param("event", "user_logged_out")
        }
        firebaseAnalytics.setUserId(null)
        preferenceHelper.clearUserLoggedIn()
    }
}
