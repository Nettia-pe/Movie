package pe.nettia.movie.data.repository

import pe.nettia.movie.data.local.dao.MovieDao
import pe.nettia.movie.data.mapper.toDomain
import pe.nettia.movie.data.mapper.toDomainCast
import pe.nettia.movie.data.remote.TmdbApi
import pe.nettia.movie.domain.model.Movie
import pe.nettia.movie.domain.model.MovieDetail
import pe.nettia.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import pe.nettia.movie.data.mapper.toEntity
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi,
    private val movieDao: MovieDao
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val response = api.getPopularMovies(page = page)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        return try {
            val detail = api.getMovieDetail(movieId)
            val credits = api.getMovieCredits(movieId)
            detail.toDomain(credits.toDomainCast())
        } catch (e: Exception) {
            // Fallback: obtener datos básicos de Room
            val entity = movieDao.getMovieById(movieId)
            if (entity != null) {
                pe.nettia.movie.domain.model.MovieDetail(
                    id = entity.id,
                    title = entity.title,
                    posterUrl = entity.posterUrl,
                    overview = entity.overview ?: "",
                    releaseDate = entity.releaseDate ?: "",
                    runtime = null,
                    genres = emptyList(),
                    voteAverage = null,
                    backdropUrl = null,
                    cast = emptyList()
                )
            } else {
                throw e
            }
        }
    }

    fun getAllMoviesFlow(): Flow<List<Movie>> =
        movieDao.getAllMovies().map { list -> list.map { it.toDomain() } }

    fun getFavoriteMoviesFlow(): Flow<List<Movie>> =
        movieDao.getFavoriteMovies().map { list -> list.map { it.toDomain() } }

    suspend fun addFavorite(movie: Movie) {
        movieDao.insertMovies(listOf(movie.toEntity(isFavorite = true)))
    }

    suspend fun removeFavorite(movieId: Int) {
        movieDao.setFavorite(movieId, false)
    }
} 