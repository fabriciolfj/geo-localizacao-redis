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
import java.lang.RuntimeException
import java.util.UUID
import kotlin.random.Random

@Service
class CalculeRangeDistanceUseCase(private val getLocationsGateway: GetLocationsGateway,
                                  private val validations: List<ValidationsExistsLocationsUseCase>) {

    private val log = KotlinLogging.logger {}

    fun execute(geolocation: Geolocation) =
        runCatching {
            val value = calculateDistance(geolocation, getLocationsGateway.process())
            log.error { "distance calculated $value" }

            value
        }.getOrElse {
            log.error { "fail process calcule distance ${it.message} || detalhes ${it.printStackTrace()}" }
            throw GetDistanceException()
        }

    private fun calculateDistance(geolocation: Geolocation, locations: RGeo<String>) : Double {
        validations.forEach { it.execute(geolocation, locations) }
            .also {
                return addTargetLocationAndCalculateDistance(geolocation, locations)
            }
    }

    private fun addTargetLocationAndCalculateDistance(geolocation: Geolocation, locations: RGeo<String>): Double {
        val bucketTarget = BUCKET_TEMP_TARGET + UUID.randomUUID().toString()
        try {

            addTargetLocations(geolocation, locations, bucketTarget)

            val sourceHash = hashGeoLocation(
                geolocation.getSourceLatitude(),
                geolocation.getSourceLongitude()
            )

            return locations.dist(sourceHash, bucketTarget, GeoUnit.KILOMETERS)
        } catch (e: Exception){
            log.error { "fail add target, case ${e.printStackTrace()}" }
            throw RuntimeException(e.message)
        } finally {
            locations.remove(bucketTarget)
        }
    }

    private fun addTargetLocations(geolocation: Geolocation, location: RGeo<String>, bucket: String) {
        location.add(geolocation.getTargetLongitude(),
            geolocation.getTargetLatitude(),
            bucket)
    }
}