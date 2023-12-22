package com.jop.calendarview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jop.calendar.CalendarView
import com.jop.calendarview.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            calendar.setCalenderViewListener(object : CalendarView.CalendarViewListener{
                override fun onSelectedDate(date: Date) {
                    tvSelectDate.text = date.toString()
                }
            })
        }
    }
}