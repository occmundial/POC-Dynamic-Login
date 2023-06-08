package com.mx.rockstar.kratospoc.core.model.kratos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class SessionResponse(
    @field:Json(name = "session_token") val sessionToken: String,
    @field:Json(name = "session") val session: Session,
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(SessionResponse::class.java).indent("  ").toJson(this)
    }

    fun toJson(): String {
        return Moshi.Builder().build().adapter(SessionResponse::class.java).toJson(this)
    }
}

@JsonClass(generateAdapter = true)
data class Session(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "active") val active: Boolean,
    @field:Json(name = "expires_at") val expiresAt: String,
    @field:Json(name = "authenticated_at") val authenticatedAt: String,
    @field:Json(name = "authenticator_assurance_level") val authenticatorAssuranceLevel: String,
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(Session::class.java).toJson(this)
    }
}
