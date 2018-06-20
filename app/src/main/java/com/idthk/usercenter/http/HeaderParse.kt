package com.idthk.usercenter.config.parse

import com.dgrlucky.log.parser.Parser

import okhttp3.Headers

/**
 * @author jakewang
 * @DESC network request header parse
 */
class HeaderParse : Parser<Headers> {
    override fun parseType(): Class<Headers> {
        return Headers::class.java
    }

    override fun parseResult(headers: Headers): String {
        val builder = StringBuilder(headers.javaClass.simpleName + " [" +
                Parser.SEPARATOR)
        for (name in headers.names()) {
            builder.append(String.format("%s = %s" + Parser.SEPARATOR,
                    name, headers.get(name)))
        }
        return builder.append("]").toString()
    }
}