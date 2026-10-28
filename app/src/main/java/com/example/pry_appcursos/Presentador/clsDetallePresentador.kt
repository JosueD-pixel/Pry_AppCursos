package com.example.pry_appcursos.Presentador

import com.example.pry_appcursos.Modelo.Cls_ModeloDetalleCurso
import com.example.pry_appcursos.Vista.CursosDetalle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsDetallePresentador(private val view: CursosDetalle) {
    private val modelo = Cls_ModeloDetalleCurso()

    fun cargarDetalle(id_curso: Int) {
        if (id_curso <= 0) {
            view.mostrarError("No se pudo identificar el curso")
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val curso = withContext(Dispatchers.IO) {
                    modelo.obtenerDetalleCurso(id_curso)
                }
                view.mostrarDetalle(curso)
            } catch (e: Exception) {
                view.mostrarError("Error de conexion: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}
