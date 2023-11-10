package com.qyh.servicebuilder.builder

import com.qyh.libapi.service.IBizAdapter
import com.qyh.libapi.service.IBizService
import com.qyh.libapi.service.IServiceAccessor
import com.qyh.libapi.service.IServiceBuilder
import com.qyh.loginservice_interface.ILoginServiceInterface
import com.qyh.userinfoservice_interface.IUserInfoServiceAdapter
import com.qyh.userinfoservice_interface.UserInfoServiceImpl

/**
 * describe:
 * @date     2023/6/30
 * @author   qiaoyh
 */
class UserInfoServiceBuilder : IServiceBuilder {
    override fun build(serviceManager: IServiceAccessor): IBizService<out IBizAdapter> {
        val userInfoServiceImpl = UserInfoServiceImpl()
        userInfoServiceImpl.injectAdapter(object : IUserInfoServiceAdapter {

            override fun isLogin(): Boolean {
                return serviceManager.getService(ILoginServiceInterface::class.java).isLogin()
            }
        })

        return userInfoServiceImpl
    }
}