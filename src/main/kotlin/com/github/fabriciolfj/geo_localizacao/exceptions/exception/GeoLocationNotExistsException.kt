package com.github.fabriciolfj.geo_localizacao.exceptions.exception

import com.github.fabriciolfj.geo_localizacao.exceptions.enums.EnumError

class GeoLocationNotExistsException : RuntimeException(EnumError.ERROR_GEO_LOCATION.toMessage())