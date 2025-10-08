package com.github.fabriciolfj.geo_localizacao.adapters

import com.github.fabriciolfj.geo_localizacao.domains.usecases.GetLocationsGateway
import com.github.fabriciolfj.geo_localizacao.uil.ConstantesAdapter.LOCATIONS_KEY
import com.github.fabriciolfj.geo_localizacao.uil.HashGeoLocationUtil.hashGeoLocation
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import org.redisson.api.GeoEntry
import org.redisson.api.RGeo
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader

@Component
class GetLocationsAdapter(private val redissonClient: RedissonClient) : GetLocationsGateway {

    private val log = KotlinLogging.logger { }

    override fun process(): RGeo<String> {
        return redissonClient.getGeo(LOCATIONS_KEY)
    }

    @PostConstruct
    fun loadFile() {
        try {
            val locations: RGeo<String> = redissonClient.getGeo(LOCATIONS_KEY)
            locations.delete()

            val isLocations = javaClass.getResourceAsStream("/data/locations.csv")
            val locationsReader = BufferedReader(InputStreamReader(isLocations))

            val locationsEntries = mutableListOf<GeoEntry>()
            populate(locationsReader, locationsEntries)

            if (locationsEntries.isNotEmpty()) {
                locations.add(*locationsEntries.toTypedArray<GeoEntry>())
                log.info{ "loads ${locationsEntries.size }"}
            }

            locationsReader.close()
        } catch (e: Exception) {
            log.info{"erro load locations ${e.message}" }
        }
    }

    private fun populate(locationReader: BufferedReader, locations: MutableList<GeoEntry>) {
        var line: String?
        while ((locationReader.readLine().also { line = it }) != null) {
            if (line!!.isEmpty()) {
                continue
            }

            val parts = line.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (parts.size >= 2) {
                val longitude = parts[0].trim { it <= ' ' }.toDouble()
                val latitude = parts[1].trim { it <= ' ' }.toDouble()

                locations.add(
                    GeoEntry(
                        longitude,
                        latitude,
                        hashGeoLocation(latitude, longitude)
                    )
                )
            }
        }
    }
}