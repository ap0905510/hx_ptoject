package com.test.picasso;

import java.io.File;

/**
 * Created by Administrator on 2016/3/28.
 */
public class Test {

    public static void main(String[] args){
        File rootFile = new File(Test.class.getResource("/").getFile().replaceFirst("/", ""));
        System.out.println("rootFile = "+rootFile.getAbsolutePath());
    }
}
