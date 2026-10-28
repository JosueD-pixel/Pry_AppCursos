package com.example.pry_appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class AlumnoResponse(
    @SerialName("exito") val exito : Boolean= false,
    @SerialName("mensaje") val mensaje : String="",
    @SerialName("nombre_alumno") val nombre_alumno: String="",
    @SerialName("correo") val correo: String=""

)
