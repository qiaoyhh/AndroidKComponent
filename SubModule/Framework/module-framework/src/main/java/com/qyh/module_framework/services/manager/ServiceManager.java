package com.qyh.module_framework.services.manager;

import android.content.Context;

import com.qyh.libapi.service.IBizAdapter;
import com.qyh.libapi.service.IBizService;
import com.qyh.libapi.service.IServiceAccessor;
import com.qyh.libapi.service.IServiceBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * describe: 微服务接口创建管理
 * @date     2023/6/29
 * @author   qiaoyh
 */
public class ServiceManager implements IServiceAccessor {
    private final static String TAG = "ServiceManager";
    private HashMap<Class<? extends IBizService<? extends IBizAdapter>>,
            IBizService<? extends IBizAdapter>> allServices = new HashMap<>();
    private Context mContext;
    private ServiceFactory serviceFactory;

    public ServiceManager(Context context, Map<Class<? extends IBizService<? extends IBizAdapter>>,
            Class<? extends IServiceBuilder>> serviceConfig) {
        mContext = context;
        serviceFactory = new ServiceFactory();
        serviceFactory.mergeService(serviceConfig);
    }

    @Override
    public <T extends IBizService<? extends IBizAdapter>> T getService(Class<? extends T> serviceInterface) {
        //当前容器是否已经存在该服务
        if (allServices.containsKey(serviceInterface)) {
            return (T) allServices.get(serviceInterface);
        }

        IBizService<? extends IBizAdapter> obj = serviceFactory.createService(mContext, serviceInterface, this);
        if(obj != null){
            allServices.put(serviceInterface, obj);
            return (T)obj;
        }
        return null;
    }

    @Override
    public <T extends IBizService<? extends IBizAdapter>> void removeService(Class<? extends T> serviceInterface) {
        allServices.remove(serviceInterface);
    }

    @Override
    public Map<Class<? extends IBizService<? extends IBizAdapter>>, IBizService<? extends IBizAdapter>> getAllAvailableService() {
        return allServices;
    }
}
