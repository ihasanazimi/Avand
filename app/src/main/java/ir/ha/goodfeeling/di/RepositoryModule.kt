package ir.ha.goodfeeling.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.goodfeeling.data.remote.webServices.WeatherApiWebServices
import ir.ha.goodfeeling.data.repository.weather.WeatherRepository
import ir.ha.goodfeeling.data.repository.weather.WeatherRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDeveloperInfoRepository(webServices: WeatherApiWebServices) : WeatherRepository{
        return WeatherRepositoryImpl(webServices)
    }

}