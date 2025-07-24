package pe.nettia.movie.data.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import pe.nettia.movie.data.remote.TmdbApi;
import pe.nettia.movie.domain.repository.MovieRepository;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DataModule_ProvideMovieRepositoryFactory implements Factory<MovieRepository> {
  private final Provider<TmdbApi> apiProvider;

  public DataModule_ProvideMovieRepositoryFactory(Provider<TmdbApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public MovieRepository get() {
    return provideMovieRepository(apiProvider.get());
  }

  public static DataModule_ProvideMovieRepositoryFactory create(Provider<TmdbApi> apiProvider) {
    return new DataModule_ProvideMovieRepositoryFactory(apiProvider);
  }

  public static MovieRepository provideMovieRepository(TmdbApi api) {
    return Preconditions.checkNotNullFromProvides(DataModule.INSTANCE.provideMovieRepository(api));
  }
}
