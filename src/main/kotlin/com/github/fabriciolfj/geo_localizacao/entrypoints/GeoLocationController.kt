package com.github.fabriciolfj.geo_localizacao.entrypoints

import com.github.fabriciolfj.geo_localizacao.domains.usecases.CalculeRangeDistanceUseCase
import com.github.fabriciolfj.geo_localizacao.entrypoints.GeoLocalizationRequestDTOMapper.toEntity
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api/geo")
class GeoLocationController(private val calculeRangeDistanceUseCase: CalculeRangeDistanceUseCase) {

    private val log = KotlinLogging.logger {  }


    @PostMapping
    fun calculeDistance(@Valid @RequestBody dto: GeoLocalizationRequestDTO) : CalculetedResponseDTO {
        log.info { "request received $dto" }
        val value = calculeRangeDistanceUseCase.execute(toEntity(request = dto))

        return CalculetedResponseDTO(value)
    }
}