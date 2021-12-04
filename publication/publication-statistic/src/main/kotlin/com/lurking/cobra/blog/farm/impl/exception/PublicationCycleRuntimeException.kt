package com.lurking.cobra.blog.farm.impl.exception

class PublicationCycleRuntimeException(
    private val code: Int,
    message: String,
    throwable: Throwable? = null
) : RuntimeException(message, throwable) {

    fun getErrorCode() : Int {
        return code
    }
}

public const val TECHNICAL_EXCEPTION = 100
public const val VALIDATION_EXCEPTION = 200
public const val ILLEGAL_EXCEPTION = 300