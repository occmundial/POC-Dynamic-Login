package com.mx.rockstar.kratospoc.core.model.kratos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.json.JSONObject

@JsonClass(generateAdapter = true)
data class UserInterface(
    @field:Json(name = "action") val action: String,
    @field:Json(name = "method") val method: String,
    @field:Json(name = "nodes") val nodes: List<Node>
) {
    constructor(): this("", "", emptyList())

    override fun toString(): String {
        return Moshi.Builder().build().adapter(UserInterface::class.java).toJson(this)
    }
}

@JsonClass(generateAdapter = true)
data class Node(
    @field:Json(name = "type") val type: String,
    @field:Json(name = "group") val group: String,
    @field:Json(name = "attributes") val attributes: Attributes,
    @field:Json(name = "meta") val meta: Meta? = null
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(Node::class.java).toJson(this)
    }
}

@JsonClass(generateAdapter = true)
data class Attributes(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "value") var value: String? = null,
    @field:Json(name = "required") val required: Boolean = false,
    @field:Json(name = "autocomplete") val autocomplete: String? = null,
    @field:Json(name = "disabled") val disabled: Boolean,
    @field:Json(name = "node_type") val nodeType: String
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(Attributes::class.java).toJson(this)
    }
}

@JsonClass(generateAdapter = true)
data class Meta(
    @field:Json(name = "label") val label: Label? = null
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(Meta::class.java).toJson(this)
    }
}

@JsonClass(generateAdapter = true)
data class Label(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "text") val text: String,
    @field:Json(name = "type") val type: String,
) {
    override fun toString(): String {
        return Moshi.Builder().build().adapter(Label::class.java).toJson(this)
    }
}
