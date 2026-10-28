package com.example.pry_appcursos.InterfaceApi

import com.example.pry_appcursos.Modelo.AlumnoResponse
import com.example.pry_appcursos.Modelo.RegistroRequest
import com.example.pry_appcursos.Modelo.RegistroResponse
import com.example.pry_appcursos.Modelo.Cls_Carreras
import com.example.pry_appcursos.Modelo.Cls_Cursos
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {


    //Registrar inscripcion
    @POST("inscribirse.php")
    suspend fun registrarInscripcion(@Body request: RegistroRequest): RegistroResponse

    //Lista de carreras para el Spinner
    @GET("carreras.php")
    suspend fun obtenerCarreras(): List<Cls_Carreras>

    //Cursos de una carrera (id_carrera = 0 trae todos)
    @GET("cursos.php")
    suspend fun obtenerCursos(@Query("id_carrera") id_carrera: Int): List<Cls_Cursos>

    //Buscar alumno
    @GET("buscar_alumno.php")
    suspend fun buscarAlumno(@Query("matricula") matricula: String): AlumnoResponse
}