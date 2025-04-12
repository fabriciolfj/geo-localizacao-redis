package com.github.fabriciolfj.geo_localizacao.entrypoints

import jakarta.validation.constraints.NotNull

data class GeoLocalizationRequestDTO(@field:NotNull(message = "{sourceLatitude.notnull}")
                              val sourceLatitude: Double,
                                     @field:NotNull(message = "{sourceLongitude.notnull}")
                              val sourceLongitude: Double,
                                     @field:NotNull(message = "{targetLatitude.notnull}")
                              var targetLatitude: Double,
                                     @field:NotNull(message = "{targetLongitude.notnull}")
                              val targetLongitude: Double)