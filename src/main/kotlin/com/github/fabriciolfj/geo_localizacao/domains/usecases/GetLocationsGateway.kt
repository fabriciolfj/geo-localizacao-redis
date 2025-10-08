package com.github.fabriciolfj.geo_localizacao.domains.usecases

import org.redisson.api.RGeo

fun interface GetLocationsGateway {

    fun process() : RGeo<String>
}