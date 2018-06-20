package com.idthk.usercenter.config.http.internal

import com.dgrlucky.log.LogX
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


/**
 * @author jakewang
 * @DESC log interceptor
 */
class LogInterceptor : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val start = System.nanoTime()
        val original = chain.request()
        val connect = chain.connection()
        var response = chain.proceed(original)
        try {
            var requestSb = StringBuilder()
            requestSb.append("Protocol:${connect?.protocol()}\n")
            requestSb.append("Method:${original.method()}\n")
            requestSb.append("Url:${original.url()}\n")
            val requestBody = original.body()
            if (requestBody != null) {
                requestSb.append("Content-Type:${requestBody.contentType()}\n")
                requestSb.append("Content-Length:${requestBody.contentLength()}\n")
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val charset = requestBody.contentType()?.charset(UTF8)
                if (isPlaintext(buffer) && charset != null) {
                    val body = buffer.readString(charset)
                    try {
                        LogX.json(body)
                    } catch (e: Exception) {
                        LogX.e(e.printStackTrace())
                    }
                }
            }
            val requestHeaders = original.headers()
            for (i in 0 until requestHeaders.size()) {
                val name = requestHeaders.name(i)
                if (!"Content-Type".equals(name, true) && !"Content-Length".equals(name, true)) {
                    requestSb.append("$name:${requestHeaders.value(i)}\n")
                }
            }
            LogX.i(requestSb.toString())
            val time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start)
            var responseSb = StringBuilder()
            responseSb.append("Code:${response.code()}\n")
            if (!response.message().isEmpty()) {
                responseSb.append("Message:${response.message()}\n")
            }
            responseSb.append("Connect-Time:${time}ms\n")
            val responseBody = response.body()
            val responseHeaders = response.headers()
            if (responseBody != null) {
                val contentLength = responseBody.contentLength()
                val source = responseBody.source()
                source?.request(Long.MAX_VALUE)
                var buffer = source?.buffer()
                if ("gzip".equals(responseHeaders.get("Content-Encoding"), true)) {
                    val length = buffer?.size()
                    responseSb.append("GZip-Length:${length}byte\n")
                    var gzippedResponseBody: GzipSource? = null
                    try {
                        gzippedResponseBody = GzipSource(buffer?.clone())
                        buffer = Buffer()
                        buffer.writeAll(gzippedResponseBody)
                    } finally {
                        gzippedResponseBody?.close()
                    }
                }
                val charset = responseBody.contentType()?.charset(UTF8)
                if (contentLength != 0L && charset != null) {
                    val body = buffer?.clone()?.readString(charset)
                    try {
                        LogX.json(body)
                    } catch (e: Exception) {
                        LogX.e(e.printStackTrace())
                    }
                }
            }
            for (i in 0 until responseHeaders.size()) {
                responseSb.append("${responseHeaders.name(i)}:${responseHeaders.value(i)}\n")
            }
            LogX.i(responseSb.toString())
            return response
        } catch (e: Exception) {
            LogX.e("Response Exception:" + e.printStackTrace())
        }
        return response
    }

    private fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = if (buffer.size() < 64) buffer.size() else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            e.printStackTrace()
            return false
        }
    }
}
