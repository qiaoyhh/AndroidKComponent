package com.tencent.wesing.templateservice;

import android.content.Context;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.tencent.wesing.templateservice_interface.ITemplateServiceAdapter;
import com.tencent.wesing.templateservice_interface.ITemplateServiceInterface;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws InterruptedException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //测试代码
        ITemplateServiceInterface object = new TemplateServiceImpl();
        object.injectAdapter(new ITemplateServiceAdapter() {});

        String ret = "";//object.doSomething();

        //通过断言，判断
        assertEquals("单元测试不通过，因为dosomething()返回不是this is test", "this is test", ret);
        //here is an async assert using CountDownLatch
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        }).start();
        latch.await(3, TimeUnit.SECONDS);
        assertEquals("不想等", 1, 1);    }
}
