package com.twoonethree.weatherdemo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    const val BASE_URL = "https://api.github.com/repos/square/okhttp/"

    fun getRetrofitClient(): RetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitInterface::class.java)
    }
}