package com.yw.patterns.proxy;

/**
 * 作者：create by YW
 * 日期：2018.01.31 15:41
 * 描述：
 */

public class RealObject implements Interface {
    @Override
    public void doSome() {
        System.out.println("RealObject doSome");
    }

    @Override
    public void something(String arg) {
        System.out.println("RealObject something " + arg);
    }
}
