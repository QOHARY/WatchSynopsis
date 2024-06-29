package com.example.uas.watchsynopsis.data.api

import com.example.uas.watchsynopsis.domain.Cast
import com.example.uas.watchsynopsis.domain.Crew
import com.example.uas.watchsynopsis.domain.Title
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent

class Repository() {
    //Calling of Key in the Website WatchMode
    private val apiKey = "yrihhnl6FX28Wy3pusAl8RtRaMpkGaGnOQTXvNnN"
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    private val service = watchModeApi.create(WatchModeService::class.java)

    suspend fun getReleases(): List<Title> {
        firebaseAnalytics.logEvent("get_releases") {
            param("api_key", apiKey)
        }
        return service.getReleases(apiKey).releases.map { it.toTitle() }
    }

    suspend fun getTitleDetails(titleId: Int): Title {
        firebaseAnalytics.logEvent("get_title_details") {
            param("title_id", titleId.toString())
            param("api_key", apiKey)
        }
        return service.getTitleDetails(titleId, apiKey).toTitle()
    }

    suspend fun getSearch(search: String): List<Title> {
        firebaseAnalytics.logEvent("get_search") {
            param("search_query", search)
            param("api_key", apiKey)
        }
        return service.getSearch(apiKey, search).results.map { it.toTitle() }
    }

    suspend fun getCastCrew(titleId: Int): Pair<List<Cast>, List<Crew>> {
        firebaseAnalytics.logEvent("get_cast_crew") {
            param("title_id", titleId.toString())
            param("api_key", apiKey)
        }
        val response = service.getCastCrew(titleId, apiKey)
        val cast = response.filter { it.type == "Cast" }.map { it.toCast() }
        val crew = response.filter { it.type == "Crew" }.map { it.toCrew() }
        return Pair(cast, crew)
    }
}

