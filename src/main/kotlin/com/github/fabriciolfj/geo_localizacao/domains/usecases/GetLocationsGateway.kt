package com.github.fabriciolfj.geo_localizacao.domains.usecases

import org.redisson.api.RGeo

interface GetLocationsGateway {

    fun process() : RGeo<String>
}