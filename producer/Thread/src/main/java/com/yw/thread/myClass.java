package com.yw.thread;

public class myClass {

    public static void main(String[] args) {
        Object mutex = new Object();
        new Produce(mutex).start();
        new Consumer(mutex).start();
        test();
    }

    public static void test() {}

    static class Product {
        public static String value = null;
    }

    static class Produce extends Thread {

        private Object mutex;

        public Produce(Object mutex) {
            this.mutex = mutex;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (mutex) {
                    if (null != Product.value) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Product.value = "Product No: " + System.currentTimeMillis();
                    System.out.println(Product.value);
                    mutex.notify();
                }
            }
        }
    }

    static class Consumer extends Thread {
        private Object mutex;

        public Consumer(Object mutex) {
            this.mutex = mutex;
        }
        @Override
        public void run() {
            while (true) {
                synchronized (mutex) {
                    if (null == Product.value) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Product No: " + Product.value);
                    Product.value = null;
                    mutex.notify();
                }
            }
        }
    }


}
