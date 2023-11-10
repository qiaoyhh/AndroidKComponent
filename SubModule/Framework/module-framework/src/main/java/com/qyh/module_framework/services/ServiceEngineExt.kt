package com.qyh.module_framework.services;


import com.qyh.libapi.service.IBizAdapter
import com.qyh.libapi.service.IBizService
import com.qyh.libapi.service.IServiceAccessor
import com.qyh.libapi.service.IServiceBuilder
import com.qyh.module_framework.ComponentOrServiceEmptyImpl
import kotlin.reflect.KClass

/** 简化 ServiceEngine 的 service 获取 */
val <T : IBizService<out IBizAdapter>> KClass<T>.service: T
    get() {
        return ServiceEngine.getInstance().getService(this.java)
            ?: ComponentOrServiceEmptyImpl.emptyImpl(this.java)
    }


inline fun <reified T : IBizService<out IBizAdapter>> getService() = T::class.service

/** 会返回空 Service 实现，模块调试用 */
class EmptyServiceBuilder(private val serviceInterface: Class<out IBizService<out IBizAdapter>>) :
    IServiceBuilder {

    override fun build(serviceManager: IServiceAccessor): IBizService<out IBizAdapter> {
        return ComponentOrServiceEmptyImpl.emptyImpl(serviceInterface)
    }
}