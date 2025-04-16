package ir.ha.goodfeeling.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.goodfeeling.data.remote.webServices.WeatherApiWebServices
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WebServiceModule {


    @Singleton
    @Provides
    fun provideWebServices(@Named("weather")retrofit: Retrofit.Builder) : WeatherApiWebServices {
        return retrofit.build().create(WeatherApiWebServices::class.java)
    }


}