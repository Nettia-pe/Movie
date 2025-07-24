package pe.nettia.movie.domain.usecase

import pe.nettia.movie.domain.model.MovieDetail
import pe.nettia.movie.domain.repository.MovieRepository

class GetMovieDetailUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): MovieDetail {
        return repository.getMovieDetail(movieId)
    }
} 