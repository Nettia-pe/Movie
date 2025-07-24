package pe.nettia.movie.domain.repository

import pe.nettia.movie.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1): List<Movie>
} 