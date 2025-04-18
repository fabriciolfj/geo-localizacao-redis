package com.github.fabriciolfj.geo_localizacao.entrypoints

import com.github.fabriciolfj.geo_localizacao.domains.entities.Geolocation
import com.github.fabriciolfj.geo_localizacao.domains.entities.SourceLocation
import com.github.fabriciolfj.geo_localizacao.domains.entities.TargetLocation

object GeoLocalizationRequestDTOMapper {

    fun toEntity(request: GeoLocalizationRequestDTO) = Geolocation(
        source = SourceLocation(latitude = request.sourceLatitude, longitude = request.sourceLongitude),
        target = TargetLocation(longitude = request.targetLongitude, latitude = request.targetLatitude)
    )
}