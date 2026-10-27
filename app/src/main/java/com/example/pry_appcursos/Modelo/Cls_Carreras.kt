package com.example.pry_appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cls_Carreras(
    @SerialName("id_carrera") val id_carrera: Int = 0,
    @SerialName("nombre_carrera") val nombre_carrera: String = ""
)