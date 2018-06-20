package com.idthk.usercenter.config.parse

import com.dgrlucky.log.parser.Parser
import okhttp3.Response
import java.io.IOException

/**
 * @author jakewang
 * @DESC network request okhttp parse
 */
class OkHttpResponseParse : Parser<Response> {
    override fun parseType(): Class<Response> {
        return Response::class.java
    }

    override fun parseResult(response: Response?): String? {
        if (response != null) {
            val builder = StringBuilder()
            builder.append(String.format("code = %s" + Parser.SEPARATOR, response.code()))
            builder.append(String.format("isSuccessful = %s" + Parser.SEPARATOR, response.isSuccessful))
            builder.append(String.format("url = %s" + Parser.SEPARATOR, response.request().url()))
            builder.append(String.format("message = %s" + Parser.SEPARATOR, response.message()))
            builder.append(String.format("protocol = %s" + Parser.SEPARATOR, response.protocol()))
            builder.append(String.format("header = %s" + Parser.SEPARATOR,
                    HeaderParse().parseResult(response.headers())))
            try {
                builder.append(String.format("body = %s" + Parser.SEPARATOR, response.body()!!.string()))
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return builder.toString()
        }
        return null
    }
}
