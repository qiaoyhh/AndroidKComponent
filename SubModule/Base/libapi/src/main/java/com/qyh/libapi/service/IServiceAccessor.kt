package com.qyh.libapi.service

/**
 * describe: 查找Service
 * @date     2023/6/29
 * @author   qiaoyh
 */
interface IServiceAccessor {

    /**
     * @param serviceInterface 通过Service接口查找对应的实现
     */
    fun <T : IBizService<out IBizAdapter?>?> getService(serviceInterface: Class<out T>): T

    /**
     * 获取所有可访问的服务
     */
    fun getAllAvailableService(): Map<Class<out IBizService<out IBizAdapter?>>, IBizService<out IBizAdapter>>

    fun <T : IBizService<out IBizAdapter>> removeService(serviceInterface: Class<out T?>)
}