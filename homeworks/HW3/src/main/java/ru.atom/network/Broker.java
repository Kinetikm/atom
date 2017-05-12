package ru.atom.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import ru.atom.model.Action;
import ru.atom.util.JsonHelper;

import static ru.atom.model.GameSession.playersActions;

/**
 * Created by kinetik on 02.05.17.
 */
public class Broker {
    private static final Logger log = LogManager.getLogger(Broker.class);

    private static final Broker instance = new Broker();
    private final ConnectionPool connectionPool;

    public static Broker getInstance() {
        return instance;
    }

    private Broker() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    public void receive(Session session, @NotNull String msg) {
        log.info("RECEIVED: " + msg);
        Message message = JsonHelper.fromJson(msg, Message.class);
        if (message.getTopic().equals(Topic.PLANT_BOMB)) {
            log.info("Message type: " + Topic.PLANT_BOMB.toString());
            playersActions.add(new Action(Action.Type.PLANT, ConnectionPool.getInstance().getPlayer(session)));
        } else if (message.getTopic().equals(Topic.MOVE)) {
            try {
                DirectionMessage directionMessage = JsonHelper.fromJson(message.getData(), DirectionMessage.class);
                log.info("Message type: " + Topic.MOVE.toString() + " with direction: " +
                        directionMessage.getDirection().toString());
                Action action = new Action(Action.Type.MOVE, ConnectionPool.getInstance().getPlayer(session));
                action.setDirection(directionMessage.getDirection());
            } catch (EnumConstantNotPresentException ex) {
                log.error("Bad direction");
            }
        } else {
            log.error("Bad data");
        }
    }

    public void send(@NotNull String player, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        Session session = connectionPool.getSession(player);
        connectionPool.send(session, message);
    }

    public void send(@NotNull Session session, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.send(session, message);
        log.info("Send message to " + session);
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.broadcast(message);
    }
}
