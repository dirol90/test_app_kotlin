package com.test.testapp1202.helper

import java.text.SimpleDateFormat
import java.util.*

class DateToString {
    fun dateToString(date: Date): String {
        return SimpleDateFormat(
            "dd-MMMMM-yyyy hh:mm",
            Locale("ru")
        ).format(date) // 01-март-2016 10:10
    }
}