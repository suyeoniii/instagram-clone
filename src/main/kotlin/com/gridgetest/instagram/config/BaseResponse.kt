package com.gridgetest.instagram.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

class BaseResponse<T> {
    @JsonProperty("success")
    private val success: Boolean
    @JsonProperty("message")
    private val message: String
    @JsonProperty("code")
    private val code: String

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("data")
    private var data: T? = null

    // success
    constructor(data: T) {
        this.success = BaseResponseCode.SUCCESS.success
        this.message = BaseResponseCode.SUCCESS.message
        this.code = BaseResponseCode.SUCCESS.code
        this.data = data
    }

    // fail
    constructor(status: BaseResponseCode) {
        this.success = status.success
        this.message = status.message
        this.code = status.code
    }
}


