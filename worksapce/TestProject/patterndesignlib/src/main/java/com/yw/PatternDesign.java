package com.yw;

import com.yw.prototype.AbstractSpoon;
import com.yw.prototype.SoupSpoon;

public class PatternDesign {

    public static void main(String[] args) {
        AbstractSpoon spoon = new SoupSpoon();
        AbstractSpoon clone = (AbstractSpoon) spoon.clone();
        System.out.print(clone.getSpoonName());
    }
}
