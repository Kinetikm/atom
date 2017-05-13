package ru.atom.dbhackaton.mm;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by sergey on 3/14/17.
 */
public class ThreadSafeQueue {
    private static BlockingQueue<String> instance = new LinkedBlockingQueue<>();

    public static BlockingQueue<String> getInstance() {
        return instance;
    }
}
