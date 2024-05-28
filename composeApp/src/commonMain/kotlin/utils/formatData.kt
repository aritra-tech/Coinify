package utils

import com.ionspin.kotlin.bignum.decimal.toBigDecimal

fun formatData(marketCap: Double): String {
    val suffixes = listOf("", "K", "M", "B", "T")
    var value = marketCap.toBigDecimal()
    var index = 0

    while (value >= 1000.toBigDecimal() && index < suffixes.size - 1) {
        value /= 1000.toBigDecimal()
        index++
    }
    return "${value.toPlainString().take(5)}${suffixes[index]}"
}
