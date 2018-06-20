package com.idthk.usercenter.config.http.internal

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URLDecoder
import java.util.*

/**
 * @author jakewang
 * @DESC json interceptor
 */
class JsonInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var body: RequestBody? = request.body()
        val map = HashMap<String, String>()
        if (request.body() is FormBody) {
            val formBody = request.body() as FormBody
            val size = formBody.size()
            for (i in 0 until size) {
                val name = formBody.encodedName(i)
                val value = formBody.encodedValue(i)
                val nameDecode = URLDecoder.decode(name, "UTF-8")
                val valueDecode = URLDecoder.decode(value, "UTF-8")
                map.put(nameDecode, valueDecode)
            }
            val json = Gson().toJson(map)
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        }
        val newRequest = request.newBuilder().method(request.method(), body).build()
        return chain.proceed(newRequest)
    }
}
