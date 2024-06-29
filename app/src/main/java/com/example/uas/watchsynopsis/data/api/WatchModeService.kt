package com.example.uas.watchsynopsis.data.api


import com.example.uas.watchsynopsis.data.model.CastCrewResponse
import com.example.uas.watchsynopsis.data.model.ReleaseSuccessResponse
import com.example.uas.watchsynopsis.data.model.SearchSuccessResponse
import com.example.uas.watchsynopsis.data.model.TitleDetailsSuccessResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WatchModeService {
    //Getting data of Releases
    @GET("releases/")
    suspend fun getReleases(
        @Query("apiKey") apiKey: String
    ): ReleaseSuccessResponse

    //Getting data of Title with ID
    @GET("title/{titleId}/details/")
    suspend fun getTitleDetails(
        @Path("titleId") titleId: Int,
        @Query("apiKey") apiKey: String,
    ): TitleDetailsSuccessResponse

    //Getting data for searching and make autocomplete search
    @GET("autocomplete-search/")
    suspend fun getSearch(
        @Query("apiKey") apiKey: String,
        @Query("search_value") searchValue: String,
        @Query("search_type") searchType: Int = 2
    ): SearchSuccessResponse

    //Getting data of Cast and Crew in List
    @GET("title/{titleId}/cast-crew/")
    suspend fun getCastCrew(
        @Path("titleId") titleId: Int,
        @Query("apiKey") apiKey: String
    ): List<CastCrewResponse>
}
