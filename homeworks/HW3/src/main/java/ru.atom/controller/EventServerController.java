package ru.atom.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import ru.atom.dbhackaton.mm.ThreadSafeStorage;
import ru.atom.model.GameSession;
import ru.atom.network.ConnectionPool;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kinetik on 13.05.17.
 */
@Path("/game/")
public class EventServerController {
    private static final Logger log = LogManager.getLogger(EventServerController.class);
    private static AtomicLong sessionIds = new AtomicLong(0L);

    @GET
    @Path("/start")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response startGame() throws InterruptedException {
        Thread.sleep(2_000);
        ConnectionPool globalPool = ConnectionPool.getInstance();
        List<Session> candidates = globalPool.getLastPLayers();
        GameSession session = new GameSession(0);
        session.newConnection(candidates);
        session.setId(sessionIds.getAndIncrement());
        log.info(session);
        session.start();
        ThreadSafeStorage.put(session);
        return Response.ok().build();
    }
}
