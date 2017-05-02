package ru.atom.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.gameinterfaces.Tickable;
import ru.atom.model.GameSession;
import ru.atom.network.Broker;
import ru.atom.network.Topic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Ticker {
    private static final Logger log = LogManager.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
    private long tickNumber = 0;
    private final GameSession gameSession;
    private Object lock = new Object();

    public Ticker(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public void loop() {
        while (!Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            act(FRAME_TIME);
            long elapsed = System.currentTimeMillis() - started;
            if (elapsed < FRAME_TIME) {
                log.info("All tick finish at {} ms", elapsed);
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
            } else {
                log.warn("tick lag {} ms", elapsed - FRAME_TIME);
            }
            log.info("{}: tick ", tickNumber);
            tickNumber++;
        }
    }

    private void act(long time) {
        synchronized (lock) {
            this.gameSession.tick(time);
            Broker.getInstance().broadcast(Topic.REPLICA, this.gameSession.getGameObjects());
        }
    }

    public long getTickNumber() {
        return tickNumber;
    }
}
