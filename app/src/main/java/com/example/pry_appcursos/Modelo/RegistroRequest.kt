package com.example.pry_appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistroRequest(
    @SerialName("id_curso") val id_curso: Int= 0,
    @SerialName("matricula") val matricula: String ="",
    @SerialName("correo") val correo: String ="",
    @SerialName("fecha_registro") val fecha_registro: String =""
)
