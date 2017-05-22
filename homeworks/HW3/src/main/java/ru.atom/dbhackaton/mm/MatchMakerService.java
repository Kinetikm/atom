package ru.atom.dbhackaton.mm;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.hibernate.LoginEntity;
import ru.atom.model.GameSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kinetik on 12.05.17.
 */
public class MatchMakerService implements Runnable {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);

    @Override
    public void run() {
        log.info("Started");
        List<LoginEntity> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
        while (!Thread.currentThread().isInterrupted()) {
            log.info("into while cycle");
            try {
                candidates.add(
                        ThreadSafeQueue.getInstance().poll(10_000, TimeUnit.SECONDS)
                );
            } catch (InterruptedException e) {
                log.warn("Timeout reached");
            }

            if (candidates.size() == GameSession.PLAYERS_IN_GAME) {
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                String requestUrl = "http://localhost:8082" + "/game/start";
                Request request = new Request.Builder()
                        .url(requestUrl)
                        .get()
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .build();

                OkHttpClient client = new OkHttpClient();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    log.warn("Something went wrong", e);
                }

                candidates.clear();
            }
        }
    }
}
