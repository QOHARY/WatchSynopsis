package com.example.uas.watchsynopsis.data.local

import android.app.Application
import androidx.room.Room
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    @Provides
    @Singleton
    fun provideMyDatabase(app: Application): MyDatabase {
        firebaseAnalytics.logEvent("provide_database") {
            param("database_name", "my_database")
        }
        return Room.databaseBuilder(
            app,
            MyDatabase::class.java,
            "my_database"
        ).addMigrations().build() //Updating tabel or make tabel
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: MyDatabase): Repository {
        firebaseAnalytics.logEvent("provide_repository") {
            param("repository_name", "UserRepository")
        }
        return RepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(app: Application): PreferenceHelper {
        firebaseAnalytics.logEvent("provide_preference_helper") {
            param("preference_helper_name", "PreferenceHelper")
        }
        return PreferenceHelper(app)
    }
}