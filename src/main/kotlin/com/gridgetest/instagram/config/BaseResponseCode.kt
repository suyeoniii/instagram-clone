package com.gridgetest.instagram.config

enum class BaseResponseCode(
    val success: Boolean, val code: String, val message: String
) {
    /**
     * Success
     */
    SUCCESS(true, "SUCCESS", "success"),

    /**
     * Request Error
     */
    JWT_EMPTY(false, "JWT_EMPTY", "jwt token is empty"),
    INVALID_JWT(false, "INVALID_JWT", "jwt token is invalid"),
    USER_PASSWORD_INVALID(false, "USER_PASSWORD_INVALID", "password length must be between 3 and 20 and include special characters"),
    USER_PHONE_NUMBER_LENGTH(false, "USER_PHONE_NUMBER_LENGTH", "phone number length must be less than 12"),
    USER_USERNAME_INVALID(false, "USER_USERNAME_INVALID", "username length must be less than 21"),
    USER_NICKNAME_INVALID(false, "USER_NICKNAME_INVALID", "nickname can only contain English lowercase letter, number, '_', '.' and cannot exceed 20 characters."),
    POST_IMAGE_COUNT_ERROR(false, "POST_IMAGE_COUNT_ERROR", "images must be more than 1 and less than 11."),
    POST_CONTENTS_LENGTH(false, "POST_CONTENTS_LENGTH", "post contents length must be less than 1001"),
    USER_STATUS_INVALID(false, "USER_STATUS_INVALID", "user status must be in 'ACTIVE', 'INACTIVE', 'DELETED'"),

    /**
     * Response Error
     */
    USER_NICKNAME_ALREADY_EXIST(false, "USER_NICKNAME_ALREADY_EXIST", "nickname is already exist"),
    USER_LOGIN_FAILED(false, "USER_LOGIN_FAILED", "nickname or password does not match"),
    POST_NOT_FOUND(false, "POST_NOT_FOUND", "post is not found"),
    USER_NOT_FOUND(false, "USER_NOT_FOUND", "user id not found"),
    POST_UPLOAD_FAILED(false, "POST_UPLOAD_FAILED", "post upload failed"),
    COMMENT_UPLOAD_FAILED(false, "COMMENT_UPLOAD_FAILED","comment upload failed" ),
    POST_USER_NOT_MATCH(false, "POST_USER_NOT_MATCH", "post user and login user does not match"),
    POST_UPDATE_FAILED(false, "POST_UPDATE_FAILED", "updating post failed"),
    USER_UPDATE_FAILED(false, "USER_UPDATE_FAILED", "updating user failed"),
    USER_REGISTER_FAILED(false, "USER_REGISTER_FAILED", "registering user failed"),
    COMMENT_NOT_FOUND(false, "COMMENT_NOT_FOUND", "comment not found"),
    POST_REGISTER_FAILED(false, "POST_REGISTER_FAILED", "post register failed"),

    /**
     * Other Error
     */
    INTERNAL_SERVER_ERROR(false, "INTERNAL_SERVER_ERROR", "internal server error"),

}
