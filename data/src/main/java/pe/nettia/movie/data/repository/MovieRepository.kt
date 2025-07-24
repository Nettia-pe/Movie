package pe.nettia.movie.data.repository

import pe.nettia.movie.data.model.MovieResponse

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1): MovieResponse
} 