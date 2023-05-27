package com.mx.rockstar.kratospoc.core.network.model.mapper

import com.mx.rockstar.kratospoc.core.network.model.ErrorResponse
import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message

object ErrorResponseMapper : ApiErrorModelMapper<ErrorResponse> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorResponse {
        return ErrorResponse(apiErrorResponse.statusCode.code, apiErrorResponse.message())
    }

}