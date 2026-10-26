package com.example.pry_appcursos.Modelo

class clsRegistrarModelo {
    suspend fun registrarInscripcion(id_curso: Int, matricula: String, correo: String, fecha_registro: String): RegistroResponse{
        val request = RegistroRequest(id_curso = id_curso, matricula = matricula, correo=correo, fecha_registro= fecha_registro)
        return RetrofitCliente.apiService.registrarInscripcion(request)
    }
}