package pe.nettia.movie.domain.usecase

import pe.nettia.movie.domain.model.Movie
import pe.nettia.movie.domain.repository.MovieRepository

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): List<Movie> {
        return repository.getPopularMovies(page)
    }
} 