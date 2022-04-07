package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.BASE_URL
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val okhttpClient = OkHttpClient.Builder()
    .addInterceptor(Interceptor { chain ->
        //Interceptor for modifying the request to add the API_KEY
        val original = chain.request().url()

        val url = original.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val requestBuilder: Request.Builder = chain.request()
            .newBuilder()
            .url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    })
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okhttpClient)
    .build()

interface AsteroidApiService{
    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("start_date") startDate: String,
        @Query("end_date") endDate: String): String
}

object AsteroidApi{
    val service: AsteroidApiService by lazy{
        retrofit.create(AsteroidApiService::class.java)
    }
}