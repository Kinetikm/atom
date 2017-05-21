package ru.atom.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kinetik on 02.05.17.
 */
public class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance = new ConnectionPool();
    private static final int PARALLELISM_LEVEL = 1;
    private LinkedList<Session> lastPlayers = new LinkedList<>();

    private final ConcurrentHashMap<Session, String> pool;

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        pool = new ConcurrentHashMap<>();
    }

    public void send(@NotNull Session session, @NotNull String msg) {
        if (session.isOpen()) {
            try {
                session.getRemote().sendString(msg);
            } catch (IOException ignored) {
            }
        }
    }

    public void broadcast(@NotNull String msg) {
        pool.forEachKey(PARALLELISM_LEVEL, session -> send(session, msg));
    }

    public void broadcast(Set<Session> players, @NotNull String msg) {
        for(Session sess:players) {
            send(sess, msg);
        }
    }

    public void shutdown() {
        pool.forEachKey(PARALLELISM_LEVEL, session -> {
            if (session.isOpen()) {
                session.close();
            }
        });
    }

    public String getPlayer(Session session) {
        return pool.get(session);
    }

    public List<Session> getLastPLayers() {
        List<Session> resultLastPlayers = new ArrayList<>();
        for(int i = 0; i<PARALLELISM_LEVEL; i++) {
            resultLastPlayers.add(lastPlayers.remove());
        }
        return resultLastPlayers;
    }

    public Session getSession(String player) {
        return pool.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseGet(null);
    }

    public void add(Session session, String player) {
        if (pool.putIfAbsent(session, player) == null) {
            lastPlayers.add(session);
            log.info("{} joined", player);
        }
    }

    public void remove(Session session) {
        pool.remove(session);
    }
}
