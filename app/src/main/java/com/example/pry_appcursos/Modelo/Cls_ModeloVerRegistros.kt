package com.example.pry_appcursos.Modelo


class Cls_ModeloVerRegistros {
    suspend fun obtenerCarreras(): List<Cls_Carreras> {
        return RetrofitCliente.apiService.obtenerCarreras()
    }

    suspend fun obtenerResumenInscripciones(id_carrera: Int): Cls_ResumenInscripciones {
        return RetrofitCliente.apiService.obtenerResumenInscripciones(id_carrera)
    }
}