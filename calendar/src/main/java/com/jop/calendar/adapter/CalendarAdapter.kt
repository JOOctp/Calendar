package com.jop.calendar.adapter

import android.content.res.ColorStateList
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.jop.calendar.R
import com.jop.calendar.databinding.ItemLayoutDateBinding
import com.jop.calendar.util.DateUtil
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarAdapter(private val listener: CalendarAdapterListener): RecyclerView.Adapter<CalendarAdapter.MyViewHolder>(){
    private var items: MutableList<String> = mutableListOf()
    private var selectedItem: String? = null
    private val dateNow = Calendar.getInstance()
    private var locale = Locale("id", "ID")
    private var weekendColor : ColorStateList? = null
    private var selectedDateColor : ColorStateList? = null
    private var backgroundSelected: Int = 0

    inner class MyViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val binding = ItemLayoutDateBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_date, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", locale)
        val displayFormat = SimpleDateFormat("dd", locale)

        val value = items[position]

        holder.binding.apply {
            if(value.isNotEmpty()){
                dateNow.time = dateFormat.parse(value)
                val dateNowSTR = dateFormat.format(Calendar.getInstance().time)
                val numberOfDay = DateUtil.formatDateFromAPI(value, dateFormat, displayFormat)

                if(selectedItem != null && value == selectedItem) {
                    root.tag = "selected"
                    tvDate.setTextColor(selectedDateColor ?: ColorStateList.valueOf(holder.binding.root.context.resources.getColor(R.color.white)))
                    tvDate.setBackgroundResource(if(backgroundSelected > 0) backgroundSelected else R.drawable.bg_calender_selected)
                } else if(dateNow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || dateNow.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    root.tag = "none"
                    tvDate.setTextColor(weekendColor ?: ColorStateList.valueOf(holder.binding.root.context.resources.getColor(R.color.red_error)))
                    tvDate.setBackgroundResource(0)
                } else {
                    root.tag = "none"
                    tvDate.setTextColor(ColorStateList.valueOf(holder.binding.root.context.resources.getColor(R.color.black)))
                    tvDate.setBackgroundResource(0)
                }

                if(dateNowSTR.equals(value, true)){
                    tvDate.text = Html.fromHtml("<b>${numberOfDay}</b>")
                } else {
                    tvDate.text = numberOfDay
                }
            } else {
                tvDate.text = ""
                tvDate.setBackgroundResource(0)
            }

            root.setOnClickListener {
                if(value.isNotEmpty()){
                    root.tag = "selected"
                    tvDate.setBackgroundResource(R.drawable.bg_calender_selected)
                    listener.onSelectedDate(value)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun setData(list: MutableList<String>, locale: Locale, selectedItem: String? = null){
        this.items.addAll(list)
        this.locale = locale
        this.selectedItem = selectedItem
        notifyDataSetChanged()
    }

    fun setSelectedItem(selectedItem: String? = null){
        this.selectedItem = selectedItem
        notifyDataSetChanged()
    }

    fun setWeekEndColor(weekendColor: ColorStateList?){
        this.weekendColor = weekendColor
        notifyDataSetChanged()
    }

    fun setSelectedDateColor(weekendColor: ColorStateList?){
        this.selectedDateColor = weekendColor
        notifyDataSetChanged()
    }

    fun setBackgroundSelected(@DrawableRes backgroundSelected: Int){
        this.backgroundSelected = backgroundSelected
        notifyDataSetChanged()
    }

    fun clear(){
        this.items.clear()
        this.selectedItem = null
        notifyDataSetChanged()
    }

    interface CalendarAdapterListener{
        fun onSelectedDate(date: String)
    }
}