package com.example.uas.watchsynopsis.data.model

data class TitleDetailsSuccessResponse(
    val id: Int,
    val title: String,
    val original_title: String,
    val plot_overview: String,
    val type: String,
    val runtime_minutes: Int?,
    val year: Int?,
    val end_year: Int?,
    val release_date: String?,
    val imdb_id: String?,
    val tmdb_id: Int?,
    val tmdb_type: String?,
    val genres: List<Int>?,
    val genre_names: List<String>?,
    val user_rating: Double?,
    val critic_score: Int?,
    val us_rating: String?,
    val poster: String?,
    val backdrop: String?,
    val original_language: String?,
    val similar_titles: List<Int>?,
    val networks: List<Int>?,
    val network_names: List<String>?,
    val trailer: String?,
    val trailer_thumbnail: String?,
    val relevance_percentile: Double?,
    val sources: List<Source>?,

) {
    data class Source(
        val source_id: Int,
        val name: String,
        val type: String,
        val region: String,
        val ios_url: String?,
        val android_url: String?,
        val web_url: String,
        val format: String?,
        val price: Double?,
        val seasons: Int?,
        val episodes: Int?
    )
}

