package com.example.uas.watchsynopsis.domain

data class Title(
    val id: Int,
    val title: String,
    val poster: String?,
    val plot:String?,
    val year:Int?,
    val endYear: Int?,
    val genreNames: List<String>?,
    val userRating: Double?,
    val criticScore: Int?,
    val trailer: String?,
    var cast: List<Cast> = emptyList(),
    var crew: List<Crew> = emptyList()
    )
//Sample of Movie poster
val titlesSample = listOf(
    Title(
        id = 1,
        title = "The Shawshank Redemption",
        poster = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg",
        plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
        year = 1994,
        endYear = null,
        genreNames = listOf("Drama", "Crime"),
        userRating = 9.3,
        criticScore = 88,
        trailer = "https://www.youtube.com/watch?v=XZ8daibM3AE"
    ),
    Title(
        id = 2,
        title = "The Dark Knight",
        poster = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_UX182_CR0,0,182,268_AL_.jpg",
        plot = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
        year = 2005,
        endYear = null,
        genreNames = listOf("Drama", "Crime"),
        userRating = 9.3,
        criticScore = 88,
        trailer = "https://www.youtube.com/watch?v=XZ8daibM3AE"
    ),
    Title(
        id = 3,
        title = "The Godfather",
        poster = "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_UY268_CR1,0,182,268_AL_.jpg",
        plot = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
        year = 1956,
        endYear = null,
        genreNames = listOf("Drama", "Crime"),
        userRating = 9.3,
        criticScore = 88,
        trailer = "https://www.youtube.com/watch?v=XZ8daibM3AE"
    ),
    Title(
        id = 4,
        title = "12 Angry Men",
        poster = "https://m.media-amazon.com/images/M/MV5BMWU4N2FjNzYtNTVkNC00NzQ0LTg0MjAtYTJlMjFhNGUxZDFmXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_UX182_CR0,0,182,268_AL_.jpg",
        plot = "A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.",
        year = 1868,
        endYear = null,
        genreNames = listOf("Drama", "Crime"),
        userRating = 9.3,
        criticScore = 88,
        trailer = "https://www.youtube.com/watch?v=XZ8daibM3AE"
    ),
    Title(
        id = 5,
        title = "Schindler's List",
        poster = "https://m.media-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX182_CR0,0,182,268_AL_.jpg",
        plot = "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.",
        year = 1980,
        endYear = null,
        genreNames = listOf("Drama", "Crime"),
        userRating = 9.3,
        criticScore = 88,
        trailer = "https://www.youtube.com/watch?v=XZ8daibM3AE"
    )
)
