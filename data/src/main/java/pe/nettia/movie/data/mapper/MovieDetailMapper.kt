package pe.nettia.movie.data.mapper

import pe.nettia.movie.data.model.MovieDetailResponse
import pe.nettia.movie.data.model.CreditsResponse
import pe.nettia.movie.domain.model.MovieDetail as DomainMovieDetail
import pe.nettia.movie.domain.model.CastMember as DomainCastMember

fun MovieDetailResponse.toDomain(cast: List<DomainCastMember>): DomainMovieDetail = DomainMovieDetail(
    id = id,
    title = title,
    posterUrl = poster_path?.let { "https://image.tmdb.org/t/p/w500$it" },
    overview = overview,
    releaseDate = release_date,
    runtime = runtime,
    genres = genres?.map { it.name } ?: emptyList(),
    voteAverage = vote_average,
    backdropUrl = backdrop_path?.let { "https://image.tmdb.org/t/p/w780$it" },
    cast = cast
)

fun pe.nettia.movie.data.model.CastMember.toDomain(): DomainCastMember = DomainCastMember(
    id = id,
    name = name,
    profileUrl = profile_path?.let { "https://image.tmdb.org/t/p/w185$it" },
    character = character
)

fun CreditsResponse.toDomainCast(): List<DomainCastMember> = cast.map { it.toDomain() } 