package com.qyh.servicebuilder

import com.qyh.libapi.service.IBizAdapter
import com.qyh.libapi.service.IBizService
import com.qyh.libapi.service.IServiceBuilder
import com.qyh.loginservice_interface.ILoginServiceInterface
import com.qyh.servicebuilder.builder.LoginServiceBuilder
import com.qyh.servicebuilder.builder.UserInfoServiceBuilder
import com.qyh.userinfoservice_interface.IUserServiceInterface

/**
 * describe:
 * @date     2023/6/30
 * @author   qiaoyh
 */


typealias ServiceConfigList = Map<Class<out IBizService<out IBizAdapter>>, Class<out IServiceBuilder>?>

object ServiceConfig {

    private val defaultServiceConfig: ServiceConfigList = mapOf(
        IUserServiceInterface::class.java to UserInfoServiceBuilder::class.java,
        ILoginServiceInterface::class.java to LoginServiceBuilder::class.java
    )

    fun getServiceConfig(): ServiceConfigList {
        return defaultServiceConfig
    }
}