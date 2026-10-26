package com.example.pry_appcursos.InterfaceApi

import com.example.pry_appcursos.Modelo.RegistroRequest
import com.example.pry_appcursos.Modelo.RegistroResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


    //Registrar inscripcion
    @POST("inscribirse.php")
    suspend fun registrarInscripcion(@Body request: RegistroRequest): RegistroResponse
}