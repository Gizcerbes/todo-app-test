package com.uogames.salesautomators.test.database.util

import androidx.sqlite.SQLiteStatement

fun SQLiteStatement.bind(
    index: Int,
    any: Any?
) {
    when (any) {
        is ByteArray -> bindBlob(index, any)
        is Double -> bindDouble(index, any)
        is Float -> bindFloat(index, any)
        is Long -> bindLong(index, any)
        is Int -> bindInt(index, any)
        is Boolean -> bindBoolean(index, any)
        is String -> bindText(index, any)
        null -> bindNull(index)
        else -> {
            throw IllegalArgumentException(
                "Cannot bind $any at index $index Supported types: Null, ByteArray, " +
                        "Float, Double, Long, Int, String, Boolean"
            )
        }
    }
}