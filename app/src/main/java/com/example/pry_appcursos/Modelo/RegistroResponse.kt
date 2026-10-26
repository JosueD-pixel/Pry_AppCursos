package com.example.pry_appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistroResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String ="",
    @SerialName("id_inscripcion") val id_inscripcion: Int= 0,
    @SerialName("nombre_curso") val nombre_curso: String ="",
    @SerialName("matricula") val matricula: String ="",
    @SerialName("fecha_registro") val fecha_registro: String ="",

    )
