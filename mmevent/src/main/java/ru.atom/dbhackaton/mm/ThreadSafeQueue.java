package ru.atom.dbhackaton.mm;

import ru.atom.dbhackaton.hibernate.LoginEntity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by sergey on 3/14/17.
 */
public class ThreadSafeQueue {
    private static BlockingQueue<LoginEntity> instance = new LinkedBlockingQueue<>();

    public static BlockingQueue<LoginEntity> getInstance() {
        return instance;
    }
}
