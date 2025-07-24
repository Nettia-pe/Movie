package pe.nettia.movie.data.model

data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val overview: String?,
    val release_date: String?,
    val runtime: Int?,
    val genres: List<Genre>?,
    val vote_average: Float?,
    val backdrop_path: String?
)

data class Genre(
    val id: Int,
    val name: String
)

data class CreditsResponse(
    val id: Int,
    val cast: List<CastMember>
)

data class CastMember(
    val id: Int,
    val name: String,
    val profile_path: String?,
    val character: String?
) 