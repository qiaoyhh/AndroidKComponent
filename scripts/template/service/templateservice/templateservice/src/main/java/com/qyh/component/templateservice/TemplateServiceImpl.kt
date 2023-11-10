package com.qyh.component.templateservice

import android.content.Context
import com.qyh.component.templateservice_interface.ITemplateServiceAdapter
import com.qyh.component.templateservice_interface.ITemplateServiceInterface

class TemplateServiceImpl : ITemplateServiceInterface {
    private var context: Context? = null
    private lateinit var serviceAdapter: ITemplateServiceAdapter

    override fun onCreate(context: Context?) {
        this.context = context
    }

    override fun injectAdapter(adapter: ITemplateServiceAdapter) {
        this.serviceAdapter = adapter
    }

    override fun getAdapter(): ITemplateServiceAdapter {
        return serviceAdapter
    }

    override fun onDestroy() {
    }
}