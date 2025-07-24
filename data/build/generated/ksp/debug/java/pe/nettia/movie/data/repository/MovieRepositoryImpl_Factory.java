package pe.nettia.movie.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import pe.nettia.movie.data.remote.TmdbApi;

@ScopeMetadata
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
public final class MovieRepositoryImpl_Factory implements Factory<MovieRepositoryImpl> {
  private final Provider<TmdbApi> apiProvider;

  public MovieRepositoryImpl_Factory(Provider<TmdbApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public MovieRepositoryImpl get() {
    return newInstance(apiProvider.get());
  }

  public static MovieRepositoryImpl_Factory create(Provider<TmdbApi> apiProvider) {
    return new MovieRepositoryImpl_Factory(apiProvider);
  }

  public static MovieRepositoryImpl newInstance(TmdbApi api) {
    return new MovieRepositoryImpl(api);
  }
}
