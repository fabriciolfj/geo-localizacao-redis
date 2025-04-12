package com.github.fabriciolfj.geo_localizacao.exceptions.dto

import java.time.LocalDateTime

data class ValidationErrorResponse(
    val timestamp: LocalDateTime,
    val status: Int,
    val message: String,
    val errors: Map<String, String>
)