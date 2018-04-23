package com.yw.factory;

/**
 * 作者：create by YW
 * 日期：2017.07.25 14:07
 * 描述：抽象工厂 : 重点在此类以及它的实现类 ：可以构建不同的类型：建造厂：飞机，汽车
 */
public abstract class Factory {
    public abstract Sample creator();
    public abstract SampleModel creator(String name);
}
