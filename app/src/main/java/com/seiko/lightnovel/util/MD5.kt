package com.seiko.lightnovel.util

import java.security.MessageDigest

private val md5Instance by lazy { MessageDigest.getInstance("MD5") }

val String.md5: String
    get() {
        md5Instance.update(toByteArray())
        return md5Instance.digest().hexString
    }

val String.md5_16: String
    get() {
        return md5.substring(8, 24)
    }

val ByteArray.hexString: String
    get() {
        return StringBuilder(size).apply {
            this@hexString.forEach { bytes ->
                val value = bytes.toInt() and 0xff
                if (value < 16) append(0)
                append(value.toString(16))
            }
        }.toString()
    }
