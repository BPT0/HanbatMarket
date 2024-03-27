package com.hanbat.hanbatmarket.signin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SigninInstance {

    private const val BASE_URL = "https://hanbatmarket.loca.lt/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val signupService: SigninAPIService by lazy {
        retrofit.create(SigninAPIService::class.java)
    }

    val loginService: SigninAPIService by lazy {
        retrofit.create(SigninAPIService::class.java)
    }
}