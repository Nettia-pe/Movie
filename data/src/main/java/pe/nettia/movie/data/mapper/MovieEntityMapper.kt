package pe.nettia.movie.data.mapper

import pe.nettia.movie.data.local.entity.MovieEntity
import pe.nettia.movie.domain.model.Movie

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterUrl = posterUrl,
    overview = overview ?: "",
    releaseDate = releaseDate ?: ""
)

fun Movie.toEntity(isFavorite: Boolean = false): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterUrl = posterUrl,
    overview = overview,
    releaseDate = releaseDate,
    isFavorite = isFavorite
) 