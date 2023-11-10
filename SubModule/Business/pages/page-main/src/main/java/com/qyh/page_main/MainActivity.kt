package com.qyh.page_main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.qyh.libapi.model.CalendarModel
import com.qyh.module_framework.services.getService
import com.qyh.userinfoservice_interface.IUserServiceInterface

/**
 * describe:
 * @date     2023/6/29
 * @author   qiaoyh
 */
class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var textView_t: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val serviceBt: AppCompatButton = findViewById(R.id.next)
        textView = findViewById(R.id.tv)
        textView_t = findViewById(R.id.tv_t)
        serviceBt.setOnClickListener {
            weekList()
        }

        val next: AppCompatButton = findViewById(R.id.last)

        next.setOnClickListener {
            // 简化使用
            val lastWeekCalendars = getService<IUserServiceInterface>().getPreviousWeekCalendars()
            if (lastWeekCalendars?.isNotEmpty() == true) {
                textView_t.text = getDateStartEnd(lastWeekCalendars)
                textView.text = lastWeekCalendars.toString()
            }
        }
        weekList()
    }

    private fun weekList() {
        val currentWeekCalendars = getService<IUserServiceInterface>().getCurrentWeekCalendars()
        textView_t.text = getDateStartEnd(currentWeekCalendars)
        textView.text = currentWeekCalendars.toString()
    }


    private fun getDateStartEnd(list: MutableList<CalendarModel>?): String {
        val startCalendar = list?.get(0)
        val startMonth = String.format("%02d", startCalendar?.month)
        val startDay = String.format("%02d", startCalendar?.day)

        val endCalendar = list?.get(list.size - 1)
        val endMonth = String.format("%02d", endCalendar?.month)
        val endDay = String.format("%02d", endCalendar?.day)

        return "$startMonth/$startDay-$endMonth/$endDay"
    }
}