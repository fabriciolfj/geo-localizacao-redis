package com.github.fabriciolfj.geo_localizacao.exceptions.exception

import com.github.fabriciolfj.geo_localizacao.exceptions.enums.EnumError

class TargetNotExistsException : RuntimeException(EnumError.ERROR_TARGET_NOTFOUND.toMessage())