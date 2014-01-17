package com.datayes.pms.thread;

/**
 * Created with IntelliJ IDEA.
 * System: Ubuntu
 * User: baoan @datayes
 * Date: 1/14/14
 * Time: 3:31 PM
 */
public abstract class HibernateThread implements Runnable {

    private String name;

    public HibernateThread(String name) {
    }

    public String getName() {
        return name;
    }
}
