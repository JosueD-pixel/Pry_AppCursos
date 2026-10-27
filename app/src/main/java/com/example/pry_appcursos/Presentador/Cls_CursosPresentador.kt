package com.example.pry_appcursos.Presentador

import com.example.pry_appcursos.Modelo.Cls_Carreras
import com.example.pry_appcursos.Modelo.Cls_ModeloCatalogoCursos
import com.example.pry_appcursos.Vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Cls_CursosPresentador(private val view: MainActivity) {
    private val modelo = Cls_ModeloCatalogoCursos()

    fun cargarCarreras() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val carreras = withContext(Dispatchers.IO) {
                    modelo.obtenerCarreras()
                }
                //Opcion para ver todos los cursos (cursos.php sin filtro)
                val listaCarreras = listOf(Cls_Carreras(0, "Todas las carreras")) + carreras
                view.mostrarCarreras(listaCarreras)
            } catch (e: Exception) {
                view.mostrarError("Error de conexion: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }

    fun cargarCursos(id_carrera: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cursos = withContext(Dispatchers.IO) {
                    modelo.obtenerCursos(id_carrera)
                }
                if (cursos.isEmpty()) {
                    view.mostrarSinCursos()
                } else {
                    view.mostrarCursos(cursos)
                }
            } catch (e: Exception) {
                view.mostrarError("Error de conexion: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}