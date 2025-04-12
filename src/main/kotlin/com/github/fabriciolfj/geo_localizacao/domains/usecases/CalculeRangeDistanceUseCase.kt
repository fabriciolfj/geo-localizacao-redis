package com.github.fabriciolfj.geo_localizacao.domains.usecases

import com.github.fabriciolfj.geo_localizacao.domains.entities.Geolocation
import com.github.fabriciolfj.geo_localizacao.exceptions.exception.GeoLocationNotExistsException
import com.github.fabriciolfj.geo_localizacao.exceptions.exception.GetDistanceException
import com.github.fabriciolfj.geo_localizacao.uil.ConstantsDomain.BUCKET_TEMP_TARGET
import com.github.fabriciolfj.geo_localizacao.uil.HashGeoLocationUtil.hashGeoLocation
import io.github.oshai.kotlinlogging.KotlinLogging
import org.redisson.api.GeoPosition
import org.redisson.api.GeoUnit
import org.redisson.api.RGeo
import org.springframework.stereotype.Service

@Service
class CalculeRangeDistanceUseCase(private val getLocationsGateway: GetLocationsGateway,
                                  private val validations: List<ValidationsExistsLocationsUseCase>) {

    private val log = KotlinLogging.logger {}

    fun execute(geolocation: Geolocation) =
        runCatching {
            calculeDistance(geolocation, getLocationsGateway.process())
        }.onSuccess {
            log.info { "calculeted success $it" }
            it
        }.onFailure {
            log.error { "fail process calcule distance ${it.message}" }
            throw GetDistanceException()
        }

    private fun calculeDistance(geolocation: Geolocation, locations: RGeo<String>) : Double {
        validations.forEach { it.execute(geolocation, locations) }
            .also {
                return addTargetLocationAndCalculateDistance(geolocation, locations)
            }
    }

    private fun addTargetLocationAndCalculateDistance(geolocation: Geolocation, locations: RGeo<String>): Double {
        try {
            addTargetLocations(geolocation, locations)

            val sourceHash = hashGeoLocation(
                geolocation.getSourceLatitude(),
                geolocation.getSourceLongitude()
            )

            return locations.dist(sourceHash, BUCKET_TEMP_TARGET, GeoUnit.KILOMETERS)
        } finally {
            locations.remove(BUCKET_TEMP_TARGET)
        }
    }

    private fun addTargetLocations(geolocation: Geolocation, location: RGeo<String>): GeoPosition {
        val hash = hashGeoLocation(geolocation.getTargetLatitude(),
            geolocation.getTargetLongitude())

        location.add(geolocation.getTargetLongitude(),
            geolocation.getTargetLatitude(),
            BUCKET_TEMP_TARGET)

        return location.pos(hash)[hash] ?: throw GeoLocationNotExistsException()
    }
}