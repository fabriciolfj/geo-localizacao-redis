package com.github.fabriciolfj.geo_localizacao.exceptions.exception

import com.github.fabriciolfj.geo_localizacao.exceptions.enums.EnumError

class GetDistanceException : RuntimeException(EnumError.ERROR_GET_DISTANCE.toMessage())