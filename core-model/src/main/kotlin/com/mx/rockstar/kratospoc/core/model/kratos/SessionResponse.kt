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
        return Moshi.Builder().build().adapter(SessionResponse::class.java)
            .indent("  ")
            .toJson(this)
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
    @field:Json(name = "identity") val identity: Identity,
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(Session::class.java).toJson(this)
    }
}

@JsonClass(generateAdapter = true)
data class Identity(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "schema_id") val schemaId: String,
    @field:Json(name = "schema_url") val schemaUrl: String,
    @field:Json(name = "traits") val traits: Traits,
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(Identity::class.java).toJson(this)
    }
}

@JsonClass(generateAdapter = true)
data class Traits(
    // name: last,  first
    @field:Json(name = "name") val name: Name,
    @field:Json(name = "email") val email: String,
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(Traits::class.java).toJson(this)
    }
}

@JsonClass(generateAdapter = true)
data class Name(
    @field:Json(name = "last") val last: String,
    @field:Json(name = "first") val first: String,
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(Name::class.java).toJson(this)
    }
}