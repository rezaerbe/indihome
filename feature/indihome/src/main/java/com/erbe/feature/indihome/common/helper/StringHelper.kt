package com.erbe.feature.indihome.common.helper

import java.text.NumberFormat

fun Int.thousandSeparator(): String {
    return NumberFormat.getInstance().format(this).replace(",", ".")
}