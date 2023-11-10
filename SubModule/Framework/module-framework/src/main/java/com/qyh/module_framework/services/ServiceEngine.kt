package com.qyh.module_framework.services

import com.qyh.libapi.service.IBizAdapter
import com.qyh.libapi.service.IBizService
import com.qyh.module_framework.ComponentOrServiceEmptyImpl.emptyImpl
import com.qyh.module_framework.services.manager.ServiceManager

/**
 * describe: 微服务引擎
 * @date     2023/6/30
 * @author   qiaoyh
 */
class ServiceEngine {
    private lateinit var mServiceManager: ServiceManager

    companion object : BaseSingleton<ServiceEngine>() {
        override fun creator(): ServiceEngine {
            return ServiceEngine()
        }
    }

    fun initServiceManager(serviceManager: ServiceManager) {
        mServiceManager = serviceManager
    }

    fun <T : IBizService<out IBizAdapter?>?> getService(clazz: Class<out T>): T? {
        var service: T = mServiceManager.getService(clazz)
        if (service == null) {
            service = emptyImpl(clazz)
        }
        return service
    }
}