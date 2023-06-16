package com.mx.rockstar.kratospoc.core.model.kratos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class LoginForm(
    @field:Json(name = "identifier") val identifier: String,
    @field:Json(name = "password") val password: String,
    @field:Json(name = "method") val method: String
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(LoginForm::class.java).toJson(this)
    }
}
