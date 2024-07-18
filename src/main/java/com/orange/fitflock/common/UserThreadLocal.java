package com.orange.fitflock.common;

public class UserThreadLocal {
    private static ThreadLocal userThread = new ThreadLocal();


    public static void set(int userid) {
        userThread.set(userid);
    }

    public static int get() {
        return (int) userThread.get();
    }

    /**
     *      防止内存泄漏
     */
    public static void remove() {
        userThread.remove();
    }
}
