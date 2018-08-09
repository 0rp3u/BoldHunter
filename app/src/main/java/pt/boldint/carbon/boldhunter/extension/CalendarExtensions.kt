package pt.boldint.carbon.boldhunter.extension

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*


val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US) //TODO Locale should be the one set android

fun Calendar.setDate(date: String): Calendar{
    time = parser.parse(date)
    return this
}

fun Calendar.getYear(): Int = get(Calendar.YEAR)

fun Calendar.getMonth(): Int = get(Calendar.MONTH)

fun Calendar.getDay(): Int = get(Calendar.DAY_OF_MONTH)

fun getDate(cal: Calendar): String = parser.format(cal.time)

fun getCalendar(date: String): Calendar{
    return if(TextUtils.isEmpty(date))
        return Calendar.getInstance()

    else
        try{Calendar.getInstance().setDate(date)}catch (e:Exception){ Calendar.getInstance() }
}


fun Calendar.isToday() =
        Calendar.getInstance().getYear() == getYear() &&
        Calendar.getInstance().getMonth() == getMonth() &&
        Calendar.getInstance().getDay() == getDay()

fun Calendar.isYesterday() =
        Calendar.getInstance().getYear() == getYear() &&
        Calendar.getInstance().getMonth() == getMonth() &&
        Calendar.getInstance().getDay() == getDay() + 1