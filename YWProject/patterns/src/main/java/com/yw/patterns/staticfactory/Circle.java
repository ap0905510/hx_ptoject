package com.yw.patterns.staticfactory;

/**
 * 作者：create by YW
 * 日期：2018.01.31 14:14
 * 描述：
 */

public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("draw Circle");
    }

    @Override
    public void area() {
        System.out.println("Circle area");
    }
}
