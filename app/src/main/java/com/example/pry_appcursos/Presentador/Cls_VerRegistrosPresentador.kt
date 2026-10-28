package com.example.pry_appcursos.Presentador

import com.example.pry_appcursos.Modelo.Cls_Carreras
import com.example.pry_appcursos.Modelo.Cls_ModeloVerRegistros
import com.example.pry_appcursos.Vista.VerRegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Cls_VerRegistrosPresentador(private val view: VerRegistroActivity) {
    private val modelo = Cls_ModeloVerRegistros()

    fun cargarCarreras() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val carreras = withContext(Dispatchers.IO) {
                    modelo.obtenerCarreras()
                }
                //Opcion para ver el resumen de todos los cursos
                val listaCarreras = listOf(Cls_Carreras(0, "Todas las carreras")) + carreras
                view.mostrarCarreras(listaCarreras)
            } catch (e: Exception) {
                view.mostrarError("Error de conexion: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }

    fun cargarResumen(id_carrera: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val resumen = withContext(Dispatchers.IO) {
                    modelo.obtenerResumenInscripciones(id_carrera)
                }
                view.mostrarResumen(resumen.cursos, resumen.total)
            } catch (e: Exception) {
                view.mostrarError("Error de conexion: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}