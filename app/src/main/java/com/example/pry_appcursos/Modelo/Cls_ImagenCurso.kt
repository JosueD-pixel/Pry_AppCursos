package com.example.pry_appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cls_ImagenCurso(
    @SerialName("id_imagen") val id_imagen: Int = 0,
    @SerialName("id_curso") val id_curso: Int = 0,
    @SerialName("url_imagen") val url_imagen: String = ""
)

