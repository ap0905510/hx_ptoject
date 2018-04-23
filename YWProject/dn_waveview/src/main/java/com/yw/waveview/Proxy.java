package com.yw.waveview;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 作者：create by YW
 * 日期：2018.01.12 17:49
 * 描述：
 */

public class Proxy {

    public static interface Interface {
        void doSomething();
        void somethingElse(String arg);
    }

    public static class RealObject implements Interface {
        @Override
        public void doSomething() {
            System.out.println("doSomething");
        }

        @Override
        public void somethingElse(String arg) {
            System.out.println("somethingElse " + arg);
        }
    }

    public static class DynamicProxyHandler implements InvocationHandler {

        private Object proxied;

        public DynamicProxyHandler(Object proxied) {
            this.proxied = proxied;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("**** proxy: " + proxy.getClass() + "\t method: " + method + "\targs: " + args);
            return null;
        }
    }

}
