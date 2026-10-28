package com.example.pry_appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cls_ResumenInscripciones(
    @SerialName("total") val total: Int = 0,
    @SerialName("cursos") val cursos: List<Cls_CursoInscritos> = emptyList()
)
