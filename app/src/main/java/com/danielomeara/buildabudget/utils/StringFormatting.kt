package com.danielomeara.buildabudget.utils

import java.text.NumberFormat

fun formatDoubleAsCurrencyString(value: Double): String {
    return NumberFormat.getCurrencyInstance().format(value)
}
