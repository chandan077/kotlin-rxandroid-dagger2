package com.example.demoapp.module

/**
 * @author Chandan Kumar
 */
import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File


@Module(includes = [ContextModule::class])
class OkHttpClientModule {
    @Provides
    fun okHttpClient(cache: Cache?, httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun cache(cacheFile: File?): Cache {
        return Cache(cacheFile, 10 * 1000 * 1000) //10 MB
    }

    @Provides
    fun file(context: Context): File {
        val file = File(context.cacheDir, "HttpCache")
        file.mkdirs()
        return file
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d(
                    "HttpLogging",
                    message
                )
            })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}