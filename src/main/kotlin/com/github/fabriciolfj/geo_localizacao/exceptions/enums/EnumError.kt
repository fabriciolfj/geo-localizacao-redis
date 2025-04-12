package com.github.fabriciolfj.geo_localizacao.exceptions.enums

import java.util.ResourceBundle

enum class EnumError {

    ERROR_SOURCE_NOTFOUND,
    ERROR_TARGET_NOTFOUND,
    ERROR_GEO_LOCATION,
    ERROR_GET_DISTANCE,
    ERROR_GENERIC;

    fun toMessage() : String {
        val bundle = ResourceBundle.getBundle("exceptions")
        return bundle.getString("${this.name}.message")
    }
}