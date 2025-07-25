package pe.nettia.movie.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.nettia.movie.data.local.dao.MovieDao
import pe.nettia.movie.data.repository.MovieRepositoryImpl
import pe.nettia.movie.domain.repository.MovieRepository
import pe.nettia.movie.data.remote.TmdbApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideMovieRepository(api: TmdbApi, movieDao: MovieDao): MovieRepository =
        MovieRepositoryImpl(api, movieDao)
} 