package com.qyh.userinfoservice_interface.calendar

import android.annotation.SuppressLint
import com.qyh.libapi.model.CalendarModel
import com.qyh.userinfoservice_interface.calendar.CalendarViewDelegate.getCurrentDay
import java.text.SimpleDateFormat
import java.util.Date

/**
 * describe: 日历工具类
 *
 * @author qiaoyh
 * @date 2023/7/3
 */
object CalendarUtil {
    private const val ONE_DAY = (1000 * 3600 * 24).toLong()
    private const val WEEK_END_DIFF = 8

    @SuppressLint("SimpleDateFormat")
    fun getDate(formatStr: String?, date: Date): Int {
        val format = SimpleDateFormat(formatStr)
        return format.format(date).toInt()
    }

    /**
     * 获取一周内所有天数
     */
    fun getWeekCalendars(calendar: CalendarModel): MutableList<CalendarModel> {
        val minCalendar = java.util.Calendar.getInstance()
        minCalendar.timeInMillis = calendar.timeInMillis
        val startCalendar = CalendarModel().apply {
            year = minCalendar[java.util.Calendar.YEAR]
            month = minCalendar[java.util.Calendar.MONTH] + 1
            day = minCalendar[java.util.Calendar.DAY_OF_MONTH]
        }
        return initCalendarForWeekView(startCalendar)
    }

    /**
     * 获取上一周的视图
     *
     * @param calendar calendar
     */
    fun getPreviousWeekCalendars(calendar: CalendarModel): MutableList<CalendarModel> {
        val date = java.util.Calendar.getInstance()
        date.set(calendar.year, calendar.month - 1,calendar.day - 7)
        val startCalendar = CalendarModel().apply {
            year = date[java.util.Calendar.YEAR]
            month = date[java.util.Calendar.MONTH] + 1
            day = date[java.util.Calendar.DAY_OF_MONTH]
        }
        return initCalendarForWeekView(startCalendar)
    }

    /**
     * 生成周视图的7个item
     *
     * @param calendar 周视图的第一个日子calendar，所以往后推迟6天，生成周视图
     * @return 生成周视图的7个item
     */
    private fun initCalendarForWeekView(calendar: CalendarModel): MutableList<CalendarModel> {
        val date = java.util.Calendar.getInstance() //当天时间
        date.set(calendar.year, calendar.month - 1,calendar.day)
        val curDateMills = date.timeInMillis //生成选择的日期时间戳
        val items: MutableList<CalendarModel> = ArrayList()
        for (i in 0 until WEEK_END_DIFF) {
            date.timeInMillis = curDateMills + i * ONE_DAY
            val calendarDate = CalendarModel().apply {
                year = date[java.util.Calendar.YEAR]
                month = date[java.util.Calendar.MONTH] + 1
                day = date[java.util.Calendar.DAY_OF_MONTH]
                week = date[java.util.Calendar.DAY_OF_WEEK] - 1
            }
            if (calendarDate == getCurrentDay()) {
                calendarDate.isCurrentDay = true
            }
            if (i < WEEK_END_DIFF - 1) {
                items.add(calendarDate)
            } else {
                // 多生成一天，当做下一页的第一天
                CalendarViewDelegate.mNextCalendar = calendarDate
            }
        }

        CalendarViewDelegate.mStartCalendar = items[0]
        return items
    }
}