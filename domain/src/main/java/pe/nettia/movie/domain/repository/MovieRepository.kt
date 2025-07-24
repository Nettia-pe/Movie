package pe.nettia.movie.domain.repository

import pe.nettia.movie.domain.model.Movie
import pe.nettia.movie.domain.model.MovieDetail

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1): List<Movie>
    suspend fun getMovieDetail(movieId: Int): MovieDetail
} 