package com.github.fabriciolfj.geo_localizacao.domains.entities

data class Geolocation(val source: SourceLocation, val target: TargetLocation) {


    fun getTargetLatitude() = target.latitude

    fun getTargetLongitude() = target.longitude

    fun getSourceLatitude() = source.latitude

    fun getSourceLongitude() = source.longitude
}