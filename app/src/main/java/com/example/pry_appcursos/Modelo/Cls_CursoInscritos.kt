package com.example.pry_appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cls_CursoInscritos(
    @SerialName("id_curso") val id_curso: Int = 0,
    @SerialName("nombre_curso") val nombre_curso: String = "",
    @SerialName("cupo") val cupo: Int = 0,
    @SerialName("url_imagen_principal") val url_imagen_principal: String = "",
    @SerialName("nombre_carrera") val nombre_carrera: String = "",
    @SerialName("inscritos") val inscritos: Int = 0
)
