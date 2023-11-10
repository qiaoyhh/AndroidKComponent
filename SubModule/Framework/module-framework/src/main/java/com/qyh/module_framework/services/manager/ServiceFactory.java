package com.qyh.module_framework.services.manager;

import android.content.Context;

import com.qyh.libapi.service.IBizAdapter;
import com.qyh.libapi.service.IBizService;
import com.qyh.libapi.service.IServiceAccessor;
import com.qyh.libapi.service.IServiceBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * describe:微服务创建工厂
 *
 * @author qiaoyh
 * @date 2023/6/29
 */
public class ServiceFactory {
    private final Map<Class<? extends IBizService<? extends IBizAdapter>>, Class<? extends IServiceBuilder>>
            interfaceToImpl = new HashMap<>();

    public void mergeService(Map<Class<? extends IBizService<? extends IBizAdapter>>, Class<? extends IServiceBuilder>> serviceConfig) {
        interfaceToImpl.putAll(serviceConfig);
    }

    public <T extends IBizService<? extends IBizAdapter>> T createService(Context context,
                          Class<? extends T> serviceInterface, IServiceAccessor serviceAccessor) {
        Class<? extends IServiceBuilder> serviceBuilderClass = interfaceToImpl.get(serviceInterface);
        IServiceBuilder serviceBuilder = null;
        if (serviceBuilderClass != null) {
            try {
                serviceBuilder = serviceBuilderClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        if (serviceBuilder != null) {
            IBizService<? extends IBizAdapter> obj = serviceBuilder.build(serviceAccessor);
            obj.onCreate(context);
            return (T) obj;
        }
        return null;
    }
}
