package com.example.uas.watchsynopsis.data.model

data class SearchSuccessResponse(
    val results: List<Result>
) {
    data class  Result(
        val name: String,
        val relevance: Double,
        val type: String,
        val id: Int,
        val year: Int,
        val result_type: String,
        val tmdb_id: Int,
        val tmdb_type: String,
        val image_url: String
    )
}

