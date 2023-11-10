package com.qyh.libapi.service

import android.content.Context
import com.qyh.libapi.IBase

/**
 * describe: 模块对外接口定义
 * @date     2023/6/29
 * @author   qiaoyh
 */
interface IBizService<T : IBizAdapter?> : IBase {
    /**
     * 服务是否可用。如 ComponentOrServiceEmptyImpl 创建的 service 就是 false
     */
    fun getIsValid(): Boolean {
        return true
    }

    /**
     * 接口实现者构建
     * @param context 应用上下文环境,此处为Application Context
     */
    fun onCreate(context: Context?)

    /**
     * 注入对外依赖接口
     * @param adapter 适配器对象，负责对外依赖接口的实现
     */
    fun injectAdapter(adapter: T)

    fun getAdapter(): T

    /**
     * 完成清理工作
     */
    fun onDestroy()
}
