package com.qyh.module_framework.services

/**
 * @author   qiaoyh
 * @date     2023/1/12
 * 要实现单例类，就只需要继承这个 BaseSingleton 即可
 */
abstract class BaseSingleton<out T> {

    @Volatile
    private var instance: T? = null

    //抽象方法，需要我们在具体的单例子类当中实现此方法
    protected abstract fun creator(): T

    fun getInstance(): T =
        instance ?: synchronized(this) {
            instance ?: creator().also { instance = it }
        }
}
