package ir.hasanazimi.avand.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.avand.data.entities.remote.currencies.Rates
import okhttp3.OkHttpClient
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLSession

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    /* http://api.weatherapi.com/v1/ */

    @Provides
    @Singleton
    @Named("weather")
    fun provideWeatherRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    @Named("rss")
    fun provideRssApiRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://digiato.com/feed/")
            .client(okHttpClient)
            .addConverterFactory(SimpleXmlConverterFactory.create(Persister()))
    }


    @Provides
    @Singleton
    @Named("currencies")
    fun provideCurrenciesApiRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://api.unirateapi.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().serializeNulls().setLenient().create()
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        context: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .followSslRedirects(false)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .redactHeaders("Auth-Token", "Bearer")
                    .build()
            )
            .addNetworkInterceptor(StethoInterceptor())
            .hostnameVerifier { hostname: String, session: SSLSession -> true }
            .build()
    }

}