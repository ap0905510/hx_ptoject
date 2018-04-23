package com.yw.factory;

/**
 * 作者：create by YW
 * 日期：2017.07.25 13:50
 * 描述：工厂方法
 */
public abstract class ForumFactory {

    private static Object initLock = new Object();
    private static String className = "com.yw.PattenDesign";
    private static ForumFactory factory = null;
    public static ForumFactory getInstance(String authorization) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (authorization == null && authorization.length() <= 0) {
            return null;
        }

        if (factory == null) {
            synchronized (initLock) {
                if (factory == null) {
                    Class cls = Class.forName(className); //反射类加载
                    factory = (ForumFactory) cls.newInstance(); //创建实例
                }
            }
        }
        //Now, 返回proxy 用来限制授权对forum的访问
        return factory;
    }
}
