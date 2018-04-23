package com.yw.prototype;

/**
 * 作者：create by YW
 * 日期：2017.07.25 14:53
 * 描述：原型模型
 */

public abstract class AbstractSpoon implements Cloneable {

    private String spoonName;
    public void setSpoonName(String spoonName) {
        this.spoonName = spoonName;
    }
    public String getSpoonName() {
        return spoonName;
    }
    public Object clone() {
        Object object = null;
        try {
            object = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return object;
    }

}
