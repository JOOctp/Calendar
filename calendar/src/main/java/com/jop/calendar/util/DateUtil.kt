package com.jop.calendar.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun formatDateFromAPI(input: String, formatAPI: SimpleDateFormat, formatText: SimpleDateFormat): String {
        val date =  formatAPI.parse(input)
        var outputDateString: String? = null
        try {
            outputDateString = formatText.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateString!!
    }
}