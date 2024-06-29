package com.example.uas.watchsynopsis.data.api

import com.example.uas.watchsynopsis.data.model.CastCrewResponse
import com.example.uas.watchsynopsis.data.model.ReleaseSuccessResponse
import com.example.uas.watchsynopsis.data.model.SearchSuccessResponse
import com.example.uas.watchsynopsis.data.model.TitleDetailsSuccessResponse
import com.example.uas.watchsynopsis.domain.Cast
import com.example.uas.watchsynopsis.domain.Crew
import com.example.uas.watchsynopsis.domain.Title
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent

fun ReleaseSuccessResponse.Release.toTitle(): Title {
    val firebaseAnalytics = Firebase.analytics
    firebaseAnalytics.logEvent("map_release_to_title") {
        param("title_id", id.toString())
        param("title_name", title)
    }
    return Title(
        id = id,
        title = title,
        poster = poster_url,
        plot = null,
        year = null,
        endYear = null,
        genreNames = null,
        userRating = null,
        criticScore = null,
        trailer = null
    )
}

fun TitleDetailsSuccessResponse.toTitle(): Title {
    val firebaseAnalytics = Firebase.analytics
    firebaseAnalytics.logEvent("map_title_details_to_title") {
        param("title_id", id.toString())
        param("title_name", title)
    }
    return Title(
        id = id,
        title = title,
        poster = poster,
        plot = plot_overview,
        year = year,
        endYear = end_year,
        genreNames = genre_names,
        userRating = user_rating,
        criticScore = critic_score,
        trailer = trailer
    )
}

fun SearchSuccessResponse.Result.toTitle(): Title {
    val firebaseAnalytics = Firebase.analytics
    firebaseAnalytics.logEvent("map_search_result_to_title") {
        param("title_id", id.toString())
        param("title_name", name)
    }
    return Title(
        id = id,
        title = name,
        poster = image_url,
        plot = null,
        year = null,
        endYear = null,
        genreNames = null,
        userRating = null,
        criticScore = null,
        trailer = null
    )
}

fun CastCrewResponse.toCast(): Cast {
    val firebaseAnalytics = Firebase.analytics
    firebaseAnalytics.logEvent("map_cast_response_to_cast") {
        param("person_id", person_id.toString())
        param("person_name", full_name)
    }
    return Cast(
        id = person_id,
        name = full_name,
        role = role,
        headshotUrl = headshot_url
    )
}

fun CastCrewResponse.toCrew(): Crew {
    val firebaseAnalytics = Firebase.analytics
    firebaseAnalytics.logEvent("map_crew_response_to_crew") {
        param("person_id", person_id.toString())
        param("person_name", full_name)
    }
    return Crew(
        id = person_id,
        name = full_name,
        role = role,
        headshotUrl = headshot_url
    )
}