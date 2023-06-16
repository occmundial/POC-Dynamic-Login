package com.mx.rockstar.kratospoc.core.model.kratos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class RegistrationForm(
    @field:Json(name = "csrf_token") val csrfToken: String,
    @field:Json(name = "traits.email") val email: String,
    @field:Json(name = "traits.name.first") val firstName: String,
    @field:Json(name = "traits.name.last") val lastName: String,
    @field:Json(name = "password") val password: String,
    @field:Json(name = "method") val method: String
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(RegistrationForm::class.java).toJson(this)
    }
}
