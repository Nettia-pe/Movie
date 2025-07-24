package pe.nettia.movie.data.repository

import pe.nettia.movie.data.mapper.toDomain
import pe.nettia.movie.data.remote.TmdbApi
import pe.nettia.movie.domain.model.Movie
import pe.nettia.movie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val response = api.getPopularMovies(apiKey = "8a5fb6ee379456f592c10920f3ce33d3", page = page)
        return response.results.map { it.toDomain() }
    }
} 