package com.yw.patterns.staticfactory;

/**
 * 作者：create by YW
 * 日期：2018.01.31 14:16
 * 描述：
 */

public class ShapeFactory {

    public static final int SHAPE_1 = 1;
    public static final int SHAPE_2 = 2;

    public static Shape staticFactory(int type) {
        if (type == SHAPE_1) {
            return new Circle();
        } else if (type == SHAPE_2) {
            return new Rectangle();
        }
        return null;
    }

}
