package com.example.androiddemo.data.network

import com.example.androiddemo.data.responses.LeadProfileAPIResponse
import com.example.androiddemo.data.responses.LoginRequest
import com.example.androiddemo.data.responses.LoginUserData
import com.example.androiddemo.data.responses.UserList
import com.example.androiddemo.utils.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterFace {

    @POST("login")
    suspend fun doLogin(@Body request: LoginRequest): Response<LoginUserData>

    @GET("users/{id}")
    suspend fun getLeadProfile(@Path("id") id: String): Response<LeadProfileAPIResponse>

    @GET("users")
    suspend fun getAllLumpersData(@Query("page") page: String): Response<UserList>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): ApiInterFace {
            val okHttpClient =
                OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterFace::class.java)
        }
    }
}