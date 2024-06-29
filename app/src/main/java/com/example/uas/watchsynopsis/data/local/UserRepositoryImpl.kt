package com.example.uas.watchsynopsis.data.local

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val dao: UserDao,
) : Repository {

    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    override suspend fun insert(user: User) {
        firebaseAnalytics.logEvent("insert_user") {
            param("user_name", user.userName)
            param("user_email", user.email)
        }
        withContext(IO) {
            dao.insert(user)
        }
    }

    override suspend fun delete(user: User) {
        firebaseAnalytics.logEvent("delete_user") {
            param("user_id", user.id.toString())
            param("user_email", user.email)
        }
        withContext(IO) {
            dao.delete(user)
        }
    }

    override suspend fun update(user: User) {
        firebaseAnalytics.logEvent("update_user") {
            param("user_id", user.id.toString())
            param("user_email", user.email)
        }
        withContext(IO) {
            dao.update(user)
        }
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        firebaseAnalytics.logEvent("get_user_by_email_and_password") {
            param("user_email", email)
        }
        return withContext(IO) {
            dao.getUserByEmailAndPassword(email, password)
        }
    }

    override suspend fun getUserNameByEmail(email: String): String? {
        firebaseAnalytics.logEvent("get_user_name_by_email") {
            param("user_email", email)
        }
        return withContext(IO) {
            dao.getUserNameByEmail(email)
        }
    }
}
