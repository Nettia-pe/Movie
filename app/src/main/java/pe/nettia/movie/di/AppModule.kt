package pe.nettia.movie.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.nettia.movie.domain.usecase.GetPopularMoviesUseCase
import pe.nettia.movie.domain.repository.MovieRepository
import pe.nettia.movie.ui.viewmodel.MovieViewModel
import javax.inject.Singleton
import pe.nettia.movie.domain.usecase.GetMovieDetailUseCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(repository: MovieRepository): GetPopularMoviesUseCase =
        GetPopularMoviesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMovieDetailUseCase(repository: MovieRepository): GetMovieDetailUseCase =
        GetMovieDetailUseCase(repository)
} 