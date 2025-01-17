/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kvision.remote

import io.kvision.types.JsonDateSerializer
import kotlinx.browser.window
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import kotlin.js.Date

/**
 * JavaScript Object type
 */
external class Object

/**
 * Helper function for creating JavaScript objects.
 */
fun obj(init: dynamic.() -> Unit): dynamic {
    return (Object()).apply(init)
}

/**
 * JSON utility functions
 */
object Serialization {

    val plain = Json { serializersModule = serializersModuleOf(Date::class, JsonDateSerializer) }

    val nonstrict = Json {
        ignoreUnknownKeys = true
        serializersModule = serializersModuleOf(Date::class, JsonDateSerializer)
    }

    /**
     * An extension function to convert Serializable object to JS dynamic object
     * @param serializer a serializer for T
     */
    fun <T> T.toObj(serializer: SerializationStrategy<T>): dynamic {
        return kotlin.js.JSON.parse(plain.encodeToString(serializer, this))
    }
}

@Deprecated(
    "Added for compatibility. Use Serialization object instead.",
    replaceWith = ReplaceWith("Serialization", "io.kvision.remote.Serialization")
)
typealias JSON = Serialization

/**
 * Creates a websocket URL from current window.location and given path.
 */
fun getWebSocketUrl(url: String): String {
    return if (url.startsWith("https://")) {
        "wss" + url.drop(5)
    } else if (url.startsWith("http://")) {
        "ws" + url.drop(4)
    } else {
        val location = window.location
        val scheme = if (location.protocol == "https:") "wss" else "ws"
        val port = if (location.port == "8088") ":8080"
        else if (location.port != "0" && location.port != "") ":${location.port}" else ""
        "$scheme://${location.hostname}$port/$url"
    }
}
