package mozhde.shams.features.home.data

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mozhde.shams.features.home.domain.MovieListRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class MovieListDataModule {
    companion object {
        @Provides
        @Singleton
        fun provideMovieListService(retrofit: Retrofit): MovieListService =
            retrofit.create(MovieListService::class.java)
    }

    @Binds
    abstract fun provideMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository
}