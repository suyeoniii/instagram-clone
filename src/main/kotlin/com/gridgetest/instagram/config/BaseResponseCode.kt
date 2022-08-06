package com.gridgetest.instagram.config

enum class BaseResponseCode(
    val success: Boolean, val code: Int, val message: String
) {
    /**
     * 1000 : Success
     */
    SUCCESS(true, 1000, "success"),

    /**
     * 2000 : Request Error
     */
    JWT_EMPTY(false, 2000, "jwt token is empty"),
    INVALID_JWT(false, 2001, "jwt token is invalid"),
    USER_PASSWORD_INVALID(false, 2002, "password length must be between 3 and 20 and include special characters"),
    USER_PHONE_NUMBER_LENGTH(false, 2003, "phone number length must be less than 12"),
    USER_USERNAME_LENGTH(false, 2004, "phone number length must be less than 21"),
    USER_NICKNAME_INVALID(false, 2005, "nickname can only contain English lowercase letter, number, '_', '.' and cannot exceed 20 characters."),

    /**
     * 3000 : Response Error
     */
    USER_NICKNAME_ALREADY_EXIST(false, 3000, "nickname is already exist"),
    USER_LOGIN_FAILED(false, 3001, "nickname or password does not match"),

    /**
     * 4000 : Other Error
     */
    INTERNAL_SERVER_ERROR(false, 4000, "internal server error"),
}
