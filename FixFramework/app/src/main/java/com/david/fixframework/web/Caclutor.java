package com.david.fixframework.web;

import com.david.fixframework.Replace;

/**
 * Created by david on 2017/8/25.
 * 模拟  java  -->class   ---dex
 * 服务端
 * 制定caculator  修复哪个类里面具体的方法
 *
 */

public class Caclutor {
    @Replace(clazz = "com.david.fixframework.Caclutor",method = "caculator")
    public int caculator()
    {
        //10/0
        int i=1;
        int j=10;
//模拟异常产生
        return j/i;
    }

    @Replace(clazz = "com.david.fixframework.Caclutor",method = "caculator")
    public int caculator(int i)
    {
        //10/0
        int j=10;
//模拟异常产生
        return j/i;
    }
//    public int caculator1()
//    {
//     10/0    唯一需要
//        int i=1;
//        int j=10;
////模拟异常产生
//        return j/i;
//    }
}
