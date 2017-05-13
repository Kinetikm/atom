package ru.atom.dbhackaton.mm;

import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.GameSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kinetik on 12.05.17.
 */
public class MatchMakerService implements Runnable {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);
    public static AtomicLong sessionIds = new AtomicLong(0L);


    @Override
    public void run() {
        log.info("Started");
        List<String> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                candidates.add(
                        ThreadSafeQueue.getInstance().poll(10_000, TimeUnit.SECONDS)
                );
            } catch (InterruptedException e) {
                log.warn("Timeout reached");
            }

            if (candidates.size() == GameSession.PLAYERS_IN_GAME) {
                Response
            }
        }
    }
}
