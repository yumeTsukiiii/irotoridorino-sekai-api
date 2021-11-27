package fan.yumetsuki.utils

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.charset.Charset

suspend fun File.readTextAsync(charset: Charset = Charsets.UTF_8): Deferred<String> = coroutineScope {
    async {
        readText(charset)
    }
}