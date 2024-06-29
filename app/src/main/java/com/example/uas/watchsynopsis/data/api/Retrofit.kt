package com.example.uas.watchsynopsis.data.api

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val analyticsInterceptor = { chain: okhttp3.Interceptor.Chain ->
    val request = chain.request()
    firebaseAnalytics.logEvent("api_call") {
        param("url", request.url.toString())
        param("method", request.method)
    }
    chain.proceed(request)
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .addInterceptor(analyticsInterceptor)
    .build()

//Make a simple for calling in WatchModeService
internal val watchModeApi = Retrofit.Builder()
    .baseUrl("https://api.watchmode.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()