package com.mx.rockstar.kratospoc.core.network.model


data class ErrorResponse(
    val code: Int,
    val message: String?
)