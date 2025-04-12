package com.github.fabriciolfj.geo_localizacao.uil

import java.nio.ByteBuffer
import java.util.*

object HashGeoLocationUtil {

    fun hashGeoLocation(latitude: Double, longitude: Double) : String {
        val buffer = ByteBuffer.allocate(16)
        buffer.putDouble(latitude)
        buffer.putDouble(longitude)
        return Base64.getEncoder().encodeToString(buffer.array())
    }
}