package ir.hasanazimi.avand.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.avand.data.repository.NewsRssRepository
import ir.hasanazimi.avand.data.repository.WeatherRepository
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.use_cases.NewsRssUseCase
import ir.hasanazimi.avand.use_cases.NewsRssUseCaseImpl
import ir.hasanazimi.avand.use_cases.WeatherUseCase
import ir.hasanazimi.avand.use_cases.WeatherUseCaseImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideWeatherUseCase(weatherRepository: WeatherRepository) : WeatherUseCase{
        return WeatherUseCaseImpl(weatherRepository)
    }


    @Provides
    @Singleton
    fun provideNewsUseCase(newsRssRepository: NewsRssRepository , dataStoreManager: DataStoreManager) : NewsRssUseCase{
        return NewsRssUseCaseImpl(newsRssRepository , dataStoreManager)
    }

}