package com.mohaberabi.kmp.klient.platform

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

actual fun Map<String, Any?>.toJsonString(): String {
    val jsonObject = JSONObject()
    forEach { (key, value) ->
        jsonObject.put(key, value)
    }
    return jsonObject.toString()
}


fun String.toJsonObject() = JSONObject(this)
fun <T> JSONObject.safeGet(
    default: T,
    block: JSONObject.() -> T,
): T {
    return try {
        block()
    } catch (e: JSONException) {
        default
    }
}


fun JSONArray.toList(): List<String> {
    val list = mutableListOf<String>()
    for (i in 0 until this.length()) {
        list.add(this.getString(i))
    }
    return list
}

