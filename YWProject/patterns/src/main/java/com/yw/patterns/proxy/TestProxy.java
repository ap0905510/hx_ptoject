package com.yw.patterns.proxy;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * 作者：create by YW
 * 日期：2018.01.31 15:47
 * 描述：静态代理 ：代理：替另外一个对象打点一切
 */
public class TestProxy {

    /**
     * 代理
     * @param iFace
     */
    private static void consumer(Interface iFace) {
        iFace.doSome();
        iFace.something("YW");
    }

    public static void test() {
        consumer(new RealObject());
        consumer(new SimpleProxy(new RealObject())); // 再包一层策略
    }

    ///////////////////////////////////////
    interface ProxyBase {
        void f();
        void g();
        void h();
    }

    /**
     * 代理类 ： 帮别人做事的类
     */
    class Proxy implements ProxyBase {
        private ProxyBase implementation;

        public Proxy() {
            implementation = new Implementation(); // 被代理类对象 组合
        }

        // Pass method calls to the implementation:
        public void f() {
            implementation.f();
        }

        public void g() {
            implementation.g();
        }

        public void h() {
            implementation.h();
        }
    }

    class Implementation implements ProxyBase {
        public void f() {
            System.out.println("Implementation.f()");
        }

        public void g() {
            System.out.println("Implementation.g()");
        }

        public void h() {
            System.out.println("Implementation.h()");
        }
    }

    public class ProxyDemo extends TestCase {
        Proxy p = new Proxy();
        public void test() {
            // This just makes sure it will complete
            // without throwing an exception.
            p.f();
            p.g();
            p.h();
        }
    }
}
