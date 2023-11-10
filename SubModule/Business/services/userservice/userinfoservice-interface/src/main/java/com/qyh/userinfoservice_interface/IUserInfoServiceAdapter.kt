package com.qyh.userinfoservice_interface

import com.qyh.libapi.service.IBizAdapter

/**
 * describe:
 * @date     2023/6/30
 * @author   qiaoyh
 */
interface IUserInfoServiceAdapter : IBizAdapter {

    fun isLogin(): Boolean
}