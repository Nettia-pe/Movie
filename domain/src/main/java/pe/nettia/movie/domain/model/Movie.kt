package pe.nettia.movie.domain.model

import java.io.Serializable

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val overview: String,
    val releaseDate: String
) : Serializable 