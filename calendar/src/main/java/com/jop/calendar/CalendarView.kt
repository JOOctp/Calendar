package com.jop.calendar

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.GridLayoutManager
import com.jop.calendar.adapter.CalendarAdapter
import com.jop.calendar.databinding.CalenderViewBinding
import com.jop.calendar.util.DateUtil
import com.jop.calendar.util.SpacingItemColumnAdapter
import com.jop.calendar.util.TextViewDayOfWeek
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), CalendarAdapter.CalendarAdapterListener {
    private val binding = CalenderViewBinding.inflate(LayoutInflater.from(context), this)
    private var adapter: CalendarAdapter = CalendarAdapter(this)
    private var locale = Locale("id", "ID")

    private var selectedDay: String? = null
    private var listDateOfMonth: MutableList<String> = mutableListOf()
    private val listDayOfWeek: MutableList<String> = mutableListOf()
    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var dayFormat: SimpleDateFormat
    private lateinit var monthFormat: SimpleDateFormat

    private val calendar: Calendar = Calendar.getInstance()
    private var monthNow: Int = 1
    private var yearNow: Int = 2022
    private var dateNow: String = ""

    init {
        monthNow = calendar.get(Calendar.MONTH)
        yearNow = calendar.get(Calendar.YEAR)

        binding.apply {
            val gridLayout  = object : GridLayoutManager(context, 7){
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            rcCalender.layoutManager = gridLayout
            rcCalender.addItemDecoration(SpacingItemColumnAdapter(7,0, false))
            rcCalender.isFocusable = false
            rcCalender.adapter = adapter

            ivNext.setOnClickListener { getDayOfMonth(true) }
            ivPref.setOnClickListener { getDayOfMonth(false) }
        }

        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(it, R.styleable.CustomCalendarView, 0, 0)

            // COLOR
            val textWeekEndColor = styledAttributes.getColorStateList(R.styleable.CustomCalendarView_textWeekEndColor)
            val textSelectedColor = styledAttributes.getColorStateList(R.styleable.CustomCalendarView_textSelectedColor)
            val themeColor = styledAttributes.getColorStateList(R.styleable.CustomCalendarView_themeColor)

            // BACKGROUND
            val backgroundSelected = styledAttributes.getResourceId(R.styleable.CustomCalendarView_backgroundSelected, 0)

            val settingLocale = styledAttributes.getString(R.styleable.CustomCalendarView_locale)

            setLocale(Locale.Builder().setLanguageTag(settingLocale ?: "id-ID").build())
            setDateOfMonthColor(textWeekEndColor, textSelectedColor, backgroundSelected)
            setupTheme(themeColor)
            getDayOfMonth(null)
            styledAttributes.recycle()
        }
    }

    private fun setLocale(locale: Locale){
        this.locale = locale

        dateFormat = SimpleDateFormat("yyyy-MM-dd", locale)
        dayFormat = SimpleDateFormat("EEE", locale)
        monthFormat = SimpleDateFormat("MMMM yyyy", locale)

        dateNow = dateFormat.format(Date())

        listDayOfWeek.clear()

        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)

        listDayOfWeek.add(dayFormat.format(cal.time))

        for (i in 1..6){
            cal.add(Calendar.DATE, 1)
            listDayOfWeek.add(dayFormat.format(cal.time))
        }

        binding.lnDayOfWeek.removeAllViews()
    }

    private fun setDateOfMonthColor(textWeekEndColor: ColorStateList?, textSelectedColor: ColorStateList?, @DrawableRes backgroundSelected: Int?){
        val setupTextWeekEndColor = textWeekEndColor ?: ColorStateList.valueOf(context.resources.getColor(R.color.red_error))
        adapter.setupTheme(setupTextWeekEndColor,textSelectedColor, backgroundSelected)

        listDayOfWeek.forEachIndexed { index, day ->
            binding.lnDayOfWeek.addView(TextViewDayOfWeek(context, day, if(index == 0 || index == 6) setupTextWeekEndColor else ColorStateList.valueOf(context.resources.getColor(R.color.black))))
        }
    }

    private fun setupTheme(themeColor: ColorStateList?) {

    }

    private fun getDayOfMonth(isNext: Boolean?){
        listDateOfMonth.clear()
        adapter.clear()

        if(isNext != null && isNext){
            monthNow += 1
            if(monthNow > 11){
                monthNow = 0
                yearNow += 1
            }

            calendar.set(yearNow, monthNow,1)
        } else if(!(isNext == null || isNext)){
            monthNow -= 1
            if(monthNow < 0){
                monthNow = 11
                yearNow -= 1
            }

            calendar.set(yearNow, monthNow,1)
        }

        for (i in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            listDateOfMonth.add(dateFormat.format(calendar.time))
        }

        binding.tvMonth.text = monthFormat.format(calendar.time)

        val indexOfDayOfWeek = listDayOfWeek.indexOf(DateUtil.formatDateFromAPI(listDateOfMonth[0], dateFormat, dayFormat))

        for(i in 1..indexOfDayOfWeek){
            listDateOfMonth.add(0, "")
        }

        adapter.setData(listDateOfMonth, locale)
    }

    override fun onSelectedDate(date: String) {
        this.selectedDay = date
        adapter.setSelectedItem(selectedDay)
    }
}