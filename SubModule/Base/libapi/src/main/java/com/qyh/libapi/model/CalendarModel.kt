package com.qyh.libapi.model

import java.io.Serializable

/**
 * describe: 日历对象、
 * @date     2023/7/3
 * @author   qiaoyh
 */
class CalendarModel : Serializable, Comparable<CalendarModel?> {
    /**
     * 年
     */
    var year = 0

    /**
     * 月1-12
     */
    var month = 0

    /**
     * 日1-31
     */
    var day = 0

    /**
     * 是否是今天
     */
    var isCurrentDay = false

    /**
     * 星期,0-6 对应周日到周一
     */
    var week = 0

    /**
     * 获取当前日历对应时间戳
     *
     * @return getTimeInMillis
     */
    val timeInMillis: Long
        get() {
            val calendar = java.util.Calendar.getInstance()
            calendar[java.util.Calendar.YEAR] = year
            calendar[java.util.Calendar.MONTH] = month - 1
            calendar[java.util.Calendar.DAY_OF_MONTH] = day
            return calendar.timeInMillis
        }

    /**
     * 比较日期
     *
     * @param other 日期
     * @return <0 0 >0
     */
    override fun compareTo(other: CalendarModel?): Int {
        return if (other == null) {
            1
        } else toString().compareTo(other.toString())
    }

    override fun toString(): String {
        return year.toString() + "" + (if (month < 10) "0$month" else month) + "" + if (day < 10) "0$day" else day
    }


    override fun equals(other: Any?): Boolean {
        if (other != null && other is CalendarModel) {
            if (other.year == year && other.month == month && other.day == day) return true
        }
        return super.equals(other)
    }


    override fun hashCode(): Int {
        var result = year
        result = 31 * result + month
        result = 31 * result + day
        result = 31 * result + isCurrentDay.hashCode()
        result = 31 * result + week
        return result
    }
}