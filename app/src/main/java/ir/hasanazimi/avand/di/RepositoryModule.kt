package ir.hasanazimi.avand.di

import ir.hasanazimi.avand.data.repository.weather.WeatherRepository
import ir.hasanazimi.avand.data.repository.weather.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.avand.data.remote.web_services.WeatherWebServices
import ir.hasanazimi.avand.db.DataStoreManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDeveloperInfoRepository(webServices: WeatherWebServices , dataStoreManager: DataStoreManager) : WeatherRepository{
        return WeatherRepositoryImpl(webServices,dataStoreManager)
    }

}