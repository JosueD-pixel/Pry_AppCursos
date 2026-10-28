package com.example.pry_appcursos.Modelo

import com.example.pry_appcursos.InterfaceApi.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitCliente {

    const val BASE_URL = "http://172.20.10.4/cursos_api/"

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }
    val apiService: ApiService by lazy {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConfig.asConverterFactory(contentType = contentType))
            .build()
            .create(ApiService::class.java)
    }
}