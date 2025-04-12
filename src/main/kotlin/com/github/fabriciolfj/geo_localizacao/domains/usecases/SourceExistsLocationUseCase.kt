package com.github.fabriciolfj.geo_localizacao.domains.usecases

import com.github.fabriciolfj.geo_localizacao.domains.entities.Geolocation
import com.github.fabriciolfj.geo_localizacao.exceptions.exception.SourceNotExistsException
import com.github.fabriciolfj.geo_localizacao.uil.HashGeoLocationUtil.hashGeoLocation
import io.github.oshai.kotlinlogging.KotlinLogging
import org.redisson.api.RGeo
import org.springframework.stereotype.Service

@Service
class SourceExistsLocationUseCase : ValidationsExistsLocationsUseCase {

    private val log = KotlinLogging.logger {}

    override fun execute(geolocation: Geolocation, locations: RGeo<String>) {
        val hash = hashGeoLocation(geolocation.getSourceLatitude(), geolocation.getSourceLongitude())
        val position = locations.pos(hash)

        if (position == null || position.isEmpty() || position[hash] == null) {
            log.info { "source location not found $hash" }

            throw SourceNotExistsException()
        }
    }
}