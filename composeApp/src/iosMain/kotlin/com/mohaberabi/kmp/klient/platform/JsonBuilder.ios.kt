package com.mohaberabi.kmp.klient.platform

import com.mohaberabi.kmp.klient.utils.toUtf8String
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSArray
import platform.Foundation.NSDictionary
import platform.Foundation.NSJSONSerialization
import platform.Foundation.NSMutableArray
import platform.Foundation.NSMutableDictionary
import platform.Foundation.NSNull
import platform.Foundation.setValue

@OptIn(ExperimentalForeignApi::class)
actual fun Map<String, Any?>.toJsonString(): String {
    val nsDictionary = this.toNSDictionary()
    val jsonData = NSJSONSerialization.dataWithJSONObject(nsDictionary, 0u, null)
    return jsonData?.toUtf8String() ?: throw Exception("Failed to serialize to JSON")
}

fun Map<String, Any?>.toNSDictionary(): NSDictionary {
    val dictionary = NSMutableDictionary()
    forEach { (key, value) ->
        val nsValue = when (value) {
            null -> NSNull()
            is String -> value
            is Number -> value
            is Boolean -> value
            is Map<*, *> -> (value as Map<String, Any?>).toNSDictionary()
            is List<*> -> (value as List<Any?>).toNSArray()
            else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
        }
        dictionary.setValue(value = nsValue, forKey = key)
    }
    return dictionary
}

fun List<Any?>.toNSArray(): NSArray {
    val array = NSMutableArray()
    forEach { item ->
        val nsValue = when (item) {
            null -> NSNull()
            is String -> item
            is Number -> item
            is Boolean -> item
            is Map<*, *> -> (item as Map<String, Any?>).toNSDictionary()
            is List<*> -> (item as List<Any?>).toNSArray()
            else -> throw IllegalArgumentException("Unsupported type: ${item::class}")
        }
        array.addObject(nsValue)
    }
    return array
}