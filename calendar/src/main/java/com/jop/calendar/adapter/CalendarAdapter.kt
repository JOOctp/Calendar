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
    private var backgroundSelected: Int? = null

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
        dateNow.set(Calendar.HOUR_OF_DAY, 0)
        dateNow.set(Calendar.MINUTE, 0)
        dateNow.set(Calendar.SECOND, 0)

        holder.binding.apply {
            if(selectedItem != null && value == selectedItem) {
                root.tag = "selected"
                tvDate.setTextColor(selectedDateColor ?: ColorStateList.valueOf(holder.binding.root.context.resources.getColor(R.color.white)))
                tvDate.setBackgroundResource(backgroundSelected ?: R.drawable.bg_calender_selected)
            } else {
                root.tag = "none"
                tvDate.setTextColor(ColorStateList.valueOf(holder.binding.root.context.resources.getColor(R.color.black)))
                tvDate.setBackgroundResource(0)
            }

            if(value.isNotEmpty()){
                dateNow.time = dateFormat.parse(value)
                val dateNowSTR = dateFormat.format(Calendar.getInstance().time)
                val numberOfDay = DateUtil.formatDateFromAPI(value, dateFormat, displayFormat)

                if(dateNow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || dateNow.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    tvDate.setTextColor(weekendColor ?: ColorStateList.valueOf(holder.binding.root.context.resources.getColor(R.color.red_error)))
                }

                if(dateNowSTR.equals(value, true)){
                    tvDate.text = Html.fromHtml("<b>${numberOfDay}</b>")
                } else {
                    tvDate.text = numberOfDay
                }
            } else {
                tvDate.text = ""
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

    fun setData(list: MutableList<String>, locale: Locale){
        this.items.addAll(list)
        this.locale = locale
        notifyDataSetChanged()
    }

    fun setSelectedItem(selectedItem: String? = null){
        this.selectedItem = selectedItem
        notifyDataSetChanged()
    }

    fun setupTheme(weekendColor: ColorStateList?, selectedDateColor: ColorStateList?, @DrawableRes backgroundSelected: Int?){
        this.weekendColor = weekendColor
        this.selectedDateColor = selectedDateColor
        this.backgroundSelected = backgroundSelected
    }

    fun clear(){
        this.items.clear()
        this.selectedItem = null
        notifyDataSetChanged()
    }

    fun getDataOriginal() : MutableList<String>{
        return this.items
    }

    interface CalendarAdapterListener{
        fun onSelectedDate(date: String)
    }
}