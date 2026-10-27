package com.example.pry_appcursos.Modelo

class Cls_ModeloCatalogoCursos {
    suspend fun obtenerCarreras(): List<Cls_Carreras> {
        return RetrofitCliente.apiService.obtenerCarreras()
    }

    suspend fun obtenerCursos(id_carrera: Int): List<Cls_Cursos> {
        return RetrofitCliente.apiService.obtenerCursos(id_carrera)
    }
}