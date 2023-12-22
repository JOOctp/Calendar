package com.jop.calendar.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.Gravity
import android.widget.LinearLayout

class TextViewDayOfWeek(context: Context, value: String, color: ColorStateList) : androidx.appcompat.widget.AppCompatTextView(context){
    init {
        val lpx = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

        this.layoutParams = lpx
        this.text = value
        this.setTextColor(color)
        this.textAlignment = TEXT_ALIGNMENT_CENTER
        this.gravity = Gravity.CENTER
        this.setTypeface(this.typeface, Typeface.BOLD)
    }
}