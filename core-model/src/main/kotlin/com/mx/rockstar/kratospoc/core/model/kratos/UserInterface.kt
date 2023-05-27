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
        val json = JSONObject().apply {
            put("type", type)
            put("group", group)
            put("attributes", JSONObject(attributes.toString()))
            put("meta", JSONObject(meta.toString()))
        }
        return json.toString(2)
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
        val json = JSONObject().apply {
            put("name", name)
            put("type", type)
            put("value", value)
            put("required", required)
            put("autocomplete", autocomplete)
            put("disabled", disabled)
            put("node_type", nodeType)
        }
        return json.toString(2)
    }
}

@JsonClass(generateAdapter = true)
data class Meta(
    @field:Json(name = "label") val label: Label? = null
) {
    override fun toString(): String {
        val json = JSONObject().apply {
            put("label", JSONObject(label.toString()))
        }
        return json.toString(2)
    }
}

@JsonClass(generateAdapter = true)
data class Label(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "text") val text: String,
    @field:Json(name = "type") val type: String,
) {
    override fun toString(): String {
        val json = JSONObject().apply {
            put("id", id)
            put("text", text)
            put("type", type)
        }
        return json.toString(2)
    }
}
