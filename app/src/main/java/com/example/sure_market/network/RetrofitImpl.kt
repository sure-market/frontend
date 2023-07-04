package com.example.sure_market.network

import android.util.Log
import com.example.sure_market.screen.main.MainActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitImpl {
    private const val URL = "http:/192.168.0.17:8080"

    private fun client(interceptor: AppInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
//        .connectTimeout(100, TimeUnit.SECONDS)
        .connectTimeout( 100, TimeUnit.SECONDS )
        .readTimeout( 100, TimeUnit.SECONDS )
        .writeTimeout( 100, TimeUnit.SECONDS )
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client(AppInterceptor()))
        .build()

    fun getRetrofitService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val accessToken = MainApplication.prefs.getUserPref(
                "userToken",
                ""
            ) // ViewModel에서 지정한 key로 JWT 토큰을 가져온다.
            Log.d("daeYoung", "token: Bearer $accessToken")
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken") // 헤더에 authorization라는 key로 JWT 를 넣어준다.
                .build()
            proceed(newRequest)
        }
    }
}