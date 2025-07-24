package pe.nettia.movie.data.repository

import pe.nettia.movie.data.mapper.toDomain
import pe.nettia.movie.data.mapper.toDomainCast
import pe.nettia.movie.data.remote.TmdbApi
import pe.nettia.movie.domain.model.Movie
import pe.nettia.movie.domain.model.MovieDetail
import pe.nettia.movie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val response = api.getPopularMovies(page = page)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        val detail = api.getMovieDetail(movieId)
        val credits = api.getMovieCredits(movieId)
        return detail.toDomain(credits.toDomainCast())
    }
} 