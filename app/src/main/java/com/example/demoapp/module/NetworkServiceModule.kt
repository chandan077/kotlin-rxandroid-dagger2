package com.example.demoapp.module

/**
 * @author Chandan Kumar
 */
import com.example.demoapp.component.ApplicationScope
import com.example.demoapp.interfaces.RemoteApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [OkHttpClientModule::class])
class NetworkServiceModule {

    companion object {
        private const val BASE_URL = "https://digi-api.airtel.in/"
    }

    @Provides
    fun getApiService(retrofit: Retrofit): RemoteApiService {
        return retrofit.create(RemoteApiService::class.java)
    }

    @ApplicationScope
    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient?,
        gsonConverterFactory: GsonConverterFactory?, gson: Gson?
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun gson(): Gson? {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun gsonConverterFactory(gson: Gson?): GsonConverterFactory? {
        return GsonConverterFactory.create(gson)
    }
}