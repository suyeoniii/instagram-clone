package com.gridgetest.instagram.config

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler
    protected fun handleBaseException(e: BaseException): BaseResponse<Any> {
        return BaseResponse(e.baseResponseCode)
    }
}