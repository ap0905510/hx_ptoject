package com.yw.patterns.staticfactory;

/**
 * 作者：create by YW
 * 日期：2018.01.31 14:15
 * 描述：
 */

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("draw Rectangle");
    }

    @Override
    public void area() {
        System.out.println("Rectangle area");
    }
}
