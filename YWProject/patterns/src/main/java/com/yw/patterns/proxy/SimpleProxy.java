package com.yw.patterns.proxy;

/**
 * 作者：create by YW
 * 日期：2018.01.31 15:43
 * 描述：
 */

public class SimpleProxy implements Interface {

    private Interface mProxied;

    public SimpleProxy(Interface proxy) {
        mProxied = proxy; // 聚合
    }

    @Override
    public void doSome() {
        System.out.println("SimpleProxy doSome");
        mProxied.doSome();
    }

    @Override
    public void something(String arg) {
        System.out.println("SimpleProxy something");
        mProxied.something(arg);
    }
}
