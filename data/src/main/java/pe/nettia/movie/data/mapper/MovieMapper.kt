package pe.nettia.movie.data.mapper

import pe.nettia.movie.data.model.Movie as DataMovie
import pe.nettia.movie.domain.model.Movie as DomainMovie

fun DataMovie.toDomain(): DomainMovie = DomainMovie(
    id = id,
    title = title,
    posterUrl = poster_path,
    overview = overview,
    releaseDate = release_date
) 