package com.qyh.kcomponent

import android.app.Application
import com.qyh.module_framework.services.ServiceEngine
import com.qyh.module_framework.services.manager.ServiceManager
import com.qyh.servicebuilder.ServiceConfig

/**
 * describe:
 * @date     2023/6/29
 * @author   qiaoyh
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceEngine.getInstance().initServiceManager(ServiceManager(this,ServiceConfig.getServiceConfig()))
    }
}