package com.qyh.libapi.service

/**
 * describe: 模块接口构造器，主要用于减少service依赖，让builder依赖有的service,聚合各service之间数据互相通信
 * @date     2023/6/28
 * @author   qiaoyh
 */
interface IServiceBuilder {

    fun build(serviceManager: IServiceAccessor): IBizService<out IBizAdapter>
}