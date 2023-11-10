package com.qyh.module_framework

import com.qyh.libapi.IBase
import java.lang.reflect.Proxy

/**
 * describe:
 * @date     2023/6/28
 * @author   qiaoyh
 */
object ComponentOrServiceEmptyImpl {
    private val emptyMap = mutableMapOf<String, IBase>()
    private const val TAG = "ComponentEmptyImpl"

    @Suppress("UNCHECKED_CAST")
    fun <T: IBase> emptyImpl(clazz: Class<T>): T {
        var emptyObj = emptyMap[clazz.name]
        if (emptyObj == null) {
            emptyObj = Proxy.newProxyInstance(
                IBase::class.java.classLoader,
                arrayOf(clazz)) { _, method, _ ->
                when (method.returnType) {
                    Byte::class.java, Char::class.java,
                    Int::class.java, Long::class.java,
                    Float::class.java, Double::class.java -> 0
                    Boolean::class.java -> false
                    else -> null
                }
            } as T
            emptyMap[clazz.name] = emptyObj
        }
        return emptyObj as T
    }
}