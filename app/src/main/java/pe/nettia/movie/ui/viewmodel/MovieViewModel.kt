package pe.nettia.movie.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import pe.nettia.movie.domain.model.Movie
import pe.nettia.movie.domain.usecase.GetPopularMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val repository: pe.nettia.movie.data.repository.MovieRepositoryImpl
) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _loadingNextPage = MutableStateFlow(false)
    val loadingNextPage: StateFlow<Boolean> = _loadingNextPage.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentPage = 1
    private var isLastPage = false
    private var isLoadingPage = false

    init {
        loadPopularMovies()
    }

    fun loadPopularMovies(page: Int = 1) {
        viewModelScope.launch {
            if (page == 1) _loading.value = true else _loadingNextPage.value = true
            _error.value = null
            isLoadingPage = true
            try {
                val newMovies = getPopularMoviesUseCase(page)
                if (page == 1) {
                    _movies.value = newMovies
                } else {
                    _movies.value = _movies.value + newMovies
                }
                isLastPage = newMovies.isEmpty()
                currentPage = page
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                if (page == 1) _loading.value = false else _loadingNextPage.value = false
                isLoadingPage = false
            }
        }
    }

    fun loadNextPage() {
        if (!isLoadingPage && !isLastPage) {
            loadPopularMovies(currentPage + 1)
        }
    }

    val favoriteMovies = repository.getFavoriteMoviesFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addFavorite(movie: Movie) {
        viewModelScope.launch { repository.addFavorite(movie) }
    }

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch { repository.removeFavorite(movieId) }
    }
} 