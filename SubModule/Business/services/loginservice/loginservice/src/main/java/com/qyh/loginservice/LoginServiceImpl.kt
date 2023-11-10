package com.qyh.loginservice

import android.content.Context
import com.qyh.loginservice_interface.ILoginServiceAdapter
import com.qyh.loginservice_interface.ILoginServiceInterface

/**
 * describe:
 * @date     2023/6/30
 * @author   qiaoyh
 */
class LoginServiceImpl : ILoginServiceInterface {
    private var context: Context? = null
    private lateinit var serviceAdapter: ILoginServiceAdapter

    override fun isLogin(): Boolean {
        return true
    }

    override fun onCreate(context: Context?) {
        this.context = context
    }

    override fun injectAdapter(adapter: ILoginServiceAdapter) {
        this.serviceAdapter = adapter
    }

    override fun getAdapter(): ILoginServiceAdapter {
        return serviceAdapter
    }

    override fun onDestroy() {

    }
}