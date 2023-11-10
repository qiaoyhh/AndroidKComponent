package com.qyh.userinfoservice_interface

import android.content.Context
import android.widget.Toast
import com.qyh.libapi.model.CalendarModel
import com.qyh.userinfoservice_interface.calendar.CalendarUtil
import com.qyh.userinfoservice_interface.calendar.CalendarViewDelegate
import com.qyh.userinfoservice_interface.calendar.CalendarViewDelegate.getCurrentDay


/**
 * describe:
 * @date     2023/6/30
 * @author   qiaoyh
 */
class UserInfoServiceImpl : IUserServiceInterface {

    private var context: Context? = null
    private lateinit var serviceAdapter: IUserInfoServiceAdapter

    override fun getUserInfo(): String {
        val isLogin = serviceAdapter.isLogin()
        return if (isLogin) "UserInfo" else "Login"
    }

    override fun getCurrentWeekCalendars(): MutableList<CalendarModel>? {
        return CalendarViewDelegate.mNextCalendar?.let { CalendarUtil.getWeekCalendars(it) }
    }

    override fun getPreviousWeekCalendars(): MutableList<CalendarModel>? {
        if (CalendarViewDelegate.mStartCalendar == getCurrentDay()) {
            Toast.makeText(context, "已经最前方了", Toast.LENGTH_SHORT).show()
            return null
        }
        return CalendarViewDelegate.mStartCalendar?.let { CalendarUtil.getPreviousWeekCalendars(it) }
    }

    override fun onCreate(context: Context?) {
        this.context = context
    }

    override fun injectAdapter(adapter: IUserInfoServiceAdapter) {
        this.serviceAdapter = adapter
    }

    override fun getAdapter(): IUserInfoServiceAdapter {
        return serviceAdapter
    }

    override fun onDestroy() {

    }
}