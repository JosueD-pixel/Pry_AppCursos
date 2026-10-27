package com.example.pry_appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cls_Cursos(
    @SerialName("id_curso") val id_curso: Int = 0,
    @SerialName("id_carrera") val id_carrera: Int = 0,
    @SerialName("codigo_curso") val codigo_curso: String = "",
    @SerialName("nombre_curso") val nombre_curso: String = "",
    @SerialName("espacio") val espacio: String = "",
    @SerialName("url_imagen_principal") val url_imagen_principal: String = ""
)