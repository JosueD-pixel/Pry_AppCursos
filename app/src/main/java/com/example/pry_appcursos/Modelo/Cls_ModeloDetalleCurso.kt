package com.example.pry_appcursos.Modelo

class Cls_ModeloDetalleCurso {
    suspend fun obtenerDetalleCurso(id_curso: Int): Cls_DetalleCurso {
        return RetrofitCliente.apiService.obtenerDetalleCurso(id_curso)
    }
}
