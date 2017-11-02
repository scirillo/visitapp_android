package utils

import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
/**
 * Created by Santiago Cirillo on 11/2/17.
 */
object DateHelper {
    const val DF_SIMPLE_STRING = "yyyy-MM-dd"
    @JvmField val DF_SIMPLE_FORMAT = object : ThreadLocal<DateFormat>() {
        override fun initialValue(): DateFormat {
            return SimpleDateFormat(DF_SIMPLE_STRING, Locale.US)
        }
    }
}

fun dateNow(): String = Date().asString()

fun timestamp(): Long = System.currentTimeMillis()

fun dateParse(s: String): Date = DateHelper.DF_SIMPLE_FORMAT.get().parse(s, ParsePosition(0))

fun Date.asString(format: DateFormat): String = format.format(this)

fun Date.asString(format: String): String = asString(SimpleDateFormat(format, Locale.US))

fun Date.asString(): String = DateHelper.DF_SIMPLE_FORMAT.get().format(this)

fun Long.asDateString(): String = Date(this).asString()

fun addDay(dayN: Int): String{
    val c = Calendar.getInstance()
    val dayWeek = c.get(Calendar.DAY_OF_WEEK) + dayN
    val month = c.get(Calendar.MONTH) + 1
    val year = c.get(Calendar.YEAR)
    return dateParse("$year-$month-$dayWeek").asString()
}
