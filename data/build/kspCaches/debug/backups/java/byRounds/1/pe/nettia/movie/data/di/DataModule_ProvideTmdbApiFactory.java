package pe.nettia.movie.data.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import pe.nettia.movie.data.remote.TmdbApi;
import retrofit2.Retrofit;

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
public final class DataModule_ProvideTmdbApiFactory implements Factory<TmdbApi> {
  private final Provider<Retrofit> retrofitProvider;

  public DataModule_ProvideTmdbApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public TmdbApi get() {
    return provideTmdbApi(retrofitProvider.get());
  }

  public static DataModule_ProvideTmdbApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new DataModule_ProvideTmdbApiFactory(retrofitProvider);
  }

  public static TmdbApi provideTmdbApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(DataModule.INSTANCE.provideTmdbApi(retrofit));
  }
}
