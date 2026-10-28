package com.example.pry_appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class Cls_DetalleCurso(
    @SerialName("id_curso") val id_curso: Int = 0,
    @SerialName("id_carrera") val id_carrera: Int = 0,
    @SerialName("codigo_curso") val codigo_curso: String = "",
    @SerialName("nombre_curso") val nombre_curso: String = "",
    @SerialName("descripcion") val descripcion: String = "",
    @SerialName("espacio") val espacio: String = "",
    @SerialName("total_horas") val total_horas: Int = 0,
    @SerialName("dias_atencion") val dias_atencion: String = "",
    @SerialName("horario") val horario: String = "",
    @SerialName("cupo") val cupo: Int = 0,
    @SerialName("url_imagen_principal") val url_imagen_principal: String = "",
    @SerialName("imagenes") val imagenes: List<Cls_ImagenCurso> = emptyList()
)
