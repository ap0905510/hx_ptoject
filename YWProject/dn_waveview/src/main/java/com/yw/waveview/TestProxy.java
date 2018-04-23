package com.yw.waveview;

/**
 * 作者：create by YW
 * 日期：2018.01.12 17:58
 * 描述：
 */

public class TestProxy {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Proxy.RealObject real = new Proxy.RealObject();
        Proxy.Interface proxy = (Proxy.Interface) java.lang.reflect.Proxy.newProxyInstance(
                Proxy.Interface.class.getClassLoader(),
                new Class[]{Proxy.Interface.class},
                new Proxy.DynamicProxyHandler(real)
        );
    }

}
