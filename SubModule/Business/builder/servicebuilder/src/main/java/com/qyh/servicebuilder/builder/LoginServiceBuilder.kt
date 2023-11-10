package com.qyh.servicebuilder.builder

import com.qyh.libapi.service.IBizAdapter
import com.qyh.libapi.service.IBizService
import com.qyh.libapi.service.IServiceAccessor
import com.qyh.libapi.service.IServiceBuilder
import com.qyh.loginservice.LoginServiceImpl
import com.qyh.loginservice_interface.ILoginServiceAdapter

/**
 * describe:
 * @date     2023/6/30
 * @author   qiaoyh
 */
class LoginServiceBuilder : IServiceBuilder {
    override fun build(serviceManager: IServiceAccessor): IBizService<out IBizAdapter> {
        val loginServiceImpl = LoginServiceImpl()

        loginServiceImpl.injectAdapter(object : ILoginServiceAdapter {

        })
        return loginServiceImpl
    }
}