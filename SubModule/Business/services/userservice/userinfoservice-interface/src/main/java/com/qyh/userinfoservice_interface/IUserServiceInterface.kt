package com.qyh.userinfoservice_interface
import com.qyh.libapi.model.CalendarModel
import com.qyh.libapi.service.IBizService


/**
 * describe:
 * @date     2023/6/30
 * @author   qiaoyh
 */
interface IUserServiceInterface : IBizService<IUserInfoServiceAdapter> {

    fun getUserInfo(): String

    fun getCurrentWeekCalendars():MutableList<CalendarModel>?

    fun getPreviousWeekCalendars():MutableList<CalendarModel>?
}