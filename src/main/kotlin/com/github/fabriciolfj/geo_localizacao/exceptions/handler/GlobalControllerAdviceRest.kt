package com.github.fabriciolfj.geo_localizacao.exceptions.handler

import com.github.fabriciolfj.geo_localizacao.exceptions.dto.ValidationErrorResponse
import com.github.fabriciolfj.geo_localizacao.exceptions.enums.EnumError
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime

@ControllerAdvice
class GlobalControllerAdviceRest {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> {
        val errors = HashMap<String, String>()

        ex.bindingResult.fieldErrors.forEach { error: FieldError ->
            val fieldName = error.field
            val errorMessage = error.defaultMessage ?: EnumError.ERROR_GENERIC.toMessage()
            errors[fieldName] = errorMessage
        }

        val errorResponse = ValidationErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            message = EnumError.ERROR_GENERIC.toMessage(),
            errors = errors
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<ValidationErrorResponse> {
        val errors = HashMap<String, String>()

        ex.constraintViolations.forEach { violation ->
            val fieldName = violation.propertyPath.toString()
            val message = violation.message
            errors[fieldName] = message
        }

        val errorResponse = ValidationErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            message = EnumError.ERROR_GENERIC.toMessage(),
            errors = errors
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}