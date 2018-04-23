package com.yw.patterns;

import com.yw.patterns.proxy.TestProxy;
import com.yw.patterns.staticfactory.Shape;
import com.yw.patterns.staticfactory.ShapeFactory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testFactory() {
        Shape shape = ShapeFactory.staticFactory(1);
        shape.draw();
        shape.area();
    }

    @Test
    public void testProxy() {
        TestProxy.test();
    }
}