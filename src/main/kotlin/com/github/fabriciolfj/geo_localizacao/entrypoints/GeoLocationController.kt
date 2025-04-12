package com.github.fabriciolfj.geo_localizacao.entrypoints

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/geo")
class GeoLocationController {


    @PostMapping
    fun calculeDistance(@Valid @RequestBody dto: GeoLocalizationRequestDTO) {

    }
}