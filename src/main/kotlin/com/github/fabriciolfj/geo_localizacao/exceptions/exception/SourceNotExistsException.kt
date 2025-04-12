package com.github.fabriciolfj.geo_localizacao.exceptions.exception

import com.github.fabriciolfj.geo_localizacao.exceptions.enums.EnumError

class SourceNotExistsException : RuntimeException(EnumError.ERROR_SOURCE_NOTFOUND.toMessage())