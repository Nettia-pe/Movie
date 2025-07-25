package pe.nettia.movie.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pe.nettia.movie.domain.model.Movie
import pe.nettia.movie.data.repository.MovieRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MovieRepositoryImpl
) : ViewModel() {
    val favoriteMovies = repository.getFavoriteMoviesFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addFavorite(movie: Movie) {
        viewModelScope.launch { repository.addFavorite(movie) }
    }

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch { repository.removeFavorite(movieId) }
    }
} 