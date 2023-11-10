package com.qyh.userinfoservice_interface.calendar

import com.qyh.libapi.model.CalendarModel
import java.util.Date

object CalendarViewDelegate {
    /**
     * 下一周的第一天
     */
    var mNextCalendar: CalendarModel? = null

    /**
     * 当前周的第一天
     */
    var mStartCalendar: CalendarModel? = mNextCalendar

    /**
     * 今天的日期
     */
    private var mCurrentDate: CalendarModel? = null

    init {
        val calendar = java.util.Calendar.getInstance()
        mNextCalendar = CalendarModel().apply {
            year = calendar.get(java.util.Calendar.YEAR)
            month = calendar.get(java.util.Calendar.MONTH) + 1
            day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
        }
        updateCurrentDay()
    }


    @JvmStatic
    fun getCurrentDay(): CalendarModel? {
        return mCurrentDate
    }

    private fun updateCurrentDay() {
        val date = Date()
        mCurrentDate = CalendarModel().apply {
            year = CalendarUtil.getDate("yyyy", date)
            month = CalendarUtil.getDate("MM", date)
            day = CalendarUtil.getDate("dd", date)
        }
    }
}