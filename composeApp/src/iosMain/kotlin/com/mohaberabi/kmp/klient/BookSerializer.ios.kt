package com.mohaberabi.kmp.klient

import com.mohaberabi.kmp.klient.data.PostDto
import com.mohaberabi.kmp.klient.utils.toNSData
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSArray
import platform.Foundation.NSDictionary
import platform.Foundation.NSJSONReadingAllowFragments
import platform.Foundation.NSJSONSerialization
import platform.Foundation.valueForKey


fun <T> NSDictionary.safeGet(
    default: T,
    block: NSDictionary.() -> T?
): T {
    return try {
        block() ?: default
    } catch (e: Exception) {
        default
    }
}


@OptIn(ExperimentalForeignApi::class)
actual fun String.toPostDto(): PostDto {
    val jsonData = this.encodeToByteArray().toNSData()
    val jsonObject = NSJSONSerialization.JSONObjectWithData(
        jsonData,
        NSJSONReadingAllowFragments,
        null
    ) as? NSDictionary ?: throw Exception("Invalid JSON format")
    val title = jsonObject.safeGet("unknown") { valueForKey("title") as? String }

    val body = jsonObject.safeGet("unknown") { valueForKey("body") as? String }
    val userId = jsonObject.safeGet(1) { valueForKey("userId") as? Int }


    return PostDto(title, body, userId)

}