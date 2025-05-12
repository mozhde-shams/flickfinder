package mozhde.shams.features.home.data

import androidx.paging.PagingConfig
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

        @Provides
        @Singleton
        fun providePagingConfig(): PagingConfig {
            return PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                initialLoadSize = 2 * 20
            )
        }
    }

    @Binds
    abstract fun provideMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository
}