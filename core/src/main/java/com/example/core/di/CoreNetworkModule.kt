package com.example.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.core.BuildConfig
import com.example.core.DataStoreManager
import com.example.core.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreNetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor? {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            null
        }
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        return Cache(context.cacheDir, 30 * 1024 * 1024) // 10 MB cache
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor?,
        chuckerInterceptor: ChuckerInterceptor,
        cache: Cache,
        errorHandlingInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            loggingInterceptor?.let {
                builder.addInterceptor(it)
            }
            builder.addInterceptor(chuckerInterceptor)
        }
        builder.addNetworkInterceptor { chain ->
            val response = chain.proceed(chain.request())
            response.newBuilder()
                .header("Cache-Control", "public, max-age=" + 60 * 10)
                .build()
        }
        builder.addInterceptor { chain ->
            val original = chain.request()
            val requestWithHeaders = original.newBuilder()

                .build()
            chain.proceed(requestWithHeaders)
        }

        return builder
            .cache(cache)
            .addInterceptor(errorHandlingInterceptor)
            .connectTimeout(45, TimeUnit.SECONDS)

            .readTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    class ErrorHandlingInterceptor @Inject constructor(
        @ApplicationContext private val context: Context
    ) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response: Response

            try {
                response = chain.proceed(request)
            } catch (e: IOException) {
                // İnternet yoksa veya bağlantı hatası varsa
                throw IOException(context.getString(R.string.error_no_internet))
            }
            // memoryLeak için response kapatılabilir
            response.close()
            when (response.code) {
                400 -> throw ApiException(context.getString(R.string.error_bad_request), 400)
                401 -> throw ApiException(context.getString(R.string.error_unauthorized), 401)
                403 -> throw ApiException(context.getString(R.string.error_forbidden), 403)
                404 -> throw ApiException(context.getString(R.string.error_not_found), 404)
                408 -> throw ApiException(context.getString(R.string.error_timeout), 408)
                in 500..599 -> throw ApiException(
                    context.getString(R.string.error_server),
                    response.code
                )
            }

            return response
        }
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        gson: Gson,
        @BaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = ""
}