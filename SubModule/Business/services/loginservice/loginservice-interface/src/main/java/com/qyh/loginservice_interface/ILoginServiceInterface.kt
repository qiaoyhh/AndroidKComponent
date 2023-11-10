package com.qyh.loginservice_interface

import com.qyh.libapi.service.IBizService

/**
 * describe:
 * @date     2023/6/30
 * @author   qiaoyh
 */
interface ILoginServiceInterface : IBizService<ILoginServiceAdapter> {

    fun isLogin():Boolean
}