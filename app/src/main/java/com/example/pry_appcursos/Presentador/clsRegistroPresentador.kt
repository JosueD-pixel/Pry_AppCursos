package com.example.pry_appcursos.Presentador

import android.util.Patterns
import com.example.pry_appcursos.Modelo.clsRegistrarModelo
import com.example.pry_appcursos.Vista.RegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsRegistroPresentador(private val view: RegistroActivity) {
    private val modelo = clsRegistrarModelo()

    fun realizarRegistro(id_curso: Int, matricula: String, correo: String, fecha: String){
        if (id_curso <= 0) {
            view.mostrarError("No se pudo identificar el curso")
            return
        }
        if (matricula.isEmpty() || fecha.isEmpty() || correo.isEmpty()) {
            view.mostrarError("Debe llenar todos los campos del registro")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            view.mostrarError("El correo institucional no tiene un formato válido")
            return
        }

        val f = fecha.split("/")
        if(f.size!=3){
            view.mostrarError("Selecciona una fecha valida")
            return
        }
        val fechaConvertida = "${f[2]}-${f[1]}-${f[0]}"

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta= withContext(Dispatchers.IO){
                    modelo.registrarInscripcion(id_curso,matricula,correo,fechaConvertida)
                }
                if(respuesta.exito){
                    view.registroExitoso(respuesta.mensaje)
                }
                else{
                    view.mostrarError(respuesta.mensaje)
                }

            }catch (e: Exception){
                view.mostrarError("Error de conexion: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }

    }

}