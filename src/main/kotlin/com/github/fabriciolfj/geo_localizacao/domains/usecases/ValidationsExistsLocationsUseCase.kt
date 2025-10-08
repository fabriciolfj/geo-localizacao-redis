package com.github.fabriciolfj.geo_localizacao.domains.usecases

import com.github.fabriciolfj.geo_localizacao.domains.entities.Geolocation
import org.redisson.api.RGeo

fun interface ValidationsExistsLocationsUseCase {

    fun execute(geolocation: Geolocation, locations: RGeo<String>)
}