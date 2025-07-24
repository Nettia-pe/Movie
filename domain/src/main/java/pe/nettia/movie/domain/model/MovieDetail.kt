package pe.nettia.movie.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val overview: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val genres: List<String>,
    val voteAverage: Float?,
    val backdropUrl: String?,
    val cast: List<CastMember>
)

data class CastMember(
    val id: Int,
    val name: String,
    val profileUrl: String?,
    val character: String?
) 