package com.yw.factory;

/**
 * 作者：create by YW
 * 日期：2017.07.25 14:09
 * 描述：
 */
public class SimpleFactory extends Factory {

    private static SimpleFactory factory = null;

    public static SimpleFactory getFactory() {
        if (factory == null) {
            factory = new SimpleFactory();
        }
        return factory;
    }

    @Override
    public Sample creator() {
        return new SampleA();
    }

    @Override
    public SampleModel creator(String name) {
        if (name.equals("B")) {
            return new SampleB();
        } else if (name.equals("C")) {
            return new SampleC();
        }
        return null;
    }
}

