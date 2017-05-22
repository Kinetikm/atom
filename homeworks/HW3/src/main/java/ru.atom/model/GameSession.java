package ru.atom.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import ru.atom.EventHandler;
import ru.atom.controller.Ticker;
import ru.atom.gameinterfaces.GameObject;
import ru.atom.gameinterfaces.Positionable;
import ru.atom.gameinterfaces.Temporary;
import ru.atom.gameinterfaces.Tickable;
import ru.atom.geometry.Point;
import ru.atom.network.Broker;
import ru.atom.network.Topic;

import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kinetik on 02.05.17.
 */

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private HashMap<Integer, Positionable> gameObjects = new HashMap<>();
    private ArrayList<Positionable> notMovable = new ArrayList<>();
    private static LinkedList<Point> pawnStarts = new LinkedList<>();
    private static ConcurrentHashMap<Session, Integer> playersOnline = new ConcurrentHashMap<>();
    public static ConcurrentLinkedQueue<Action> playersActions = new ConcurrentLinkedQueue<>();

    private AtomicInteger gameObjectId;
    private Ticker ticker;
    private long id;

    public static int PLAYERS_IN_GAME = 1;

    static {
        pawnStarts.add(new Point(1*32,1*32));
        pawnStarts.add(new Point(1*32, 11*32));
        pawnStarts.add(new Point(15*32, 1*32));
        pawnStarts.add(new Point(15*32, 11*32));
    }

    public GameSession(int gameObjectId) {
        this.gameObjectId = new AtomicInteger(gameObjectId);
    }

    public void newConnection(List<Session> players) {
        for(Session name: players) {
            playersOnline.put(name, this.getGameObjectId());
            Pawn player = new Pawn(this.getGameObjectId(), 1, pawnStarts.remove(), 0);
            this.addGameObject(player);
        }
    }

    public List<Positionable> getGameObjects() {
        return new ArrayList<>(gameObjects.values());
    }

    public int getGameObjectId() {
        return gameObjectId.intValue();
    }

    public void addGameObject(Positionable gameObject) {
        try {
            gameObjects.put(this.getGameObjectId(), gameObject);
            log.info("Create an object " + gameObject.getClass() + " with id=" + gameObject.getId());
            this.gameObjectId.incrementAndGet();
        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgumentException with " + gameObject.getClass() + ", id = " + gameObject.getId());
        } catch (Exception ex) {
            log.error("Exception " + ex.getClass() + " with cause" + ex.getCause() + " with sttrace "
                    + ex.getStackTrace());
        }
    }

    private void addFieldElement(Positionable fieldPart) {
        try {
            this.notMovable.add(fieldPart);
            log.info("Create an fieldPart " + fieldPart.getClass() + " with id=" + fieldPart.getId());
            this.gameObjectId.incrementAndGet();
        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgumentException with " + fieldPart.getClass() + ", id = " + fieldPart.getId());
        } catch (Exception ex) {
            log.error("Exception " + ex.getClass() + " with cause" + ex.getCause() + " with sttrace "
                    + ex.getStackTrace());
        }
    }

    public void fieldInit() {
        for(int i=0; i<17; i++) {
            for(int j=0; j<13; j++) {
                if(i == 0 || j == 0 || i == 16 || j == 12) {
                    this.addFieldElement(new Wall(this.getGameObjectId(), new Point(i, j)));
                    continue;
                }
                if(i % 2 == 0 && j % 2 == 0) {
                    this.addFieldElement(new Wall(this.getGameObjectId(), new Point(i, j)));
                    continue;
                }
                if(((i == 15 || i == 1) && (j == 1 || j == 2 || j == 10 || j == 11))
                        || ((j == 1 || j == 11) && (i == 2 || i == 14))) {
                    continue;
                }
                this.addFieldElement(new Wood(this.getGameObjectId(), 0, new Point(i, j)));
            }
        }
    }

    private void sendReplika() {
        Gson gson = new Gson();
        Broker.getInstance().broadcast(playersOnline.keySet(), Topic.REPLICA, allObjects(gson));
    }

    private JsonArray allObjects(Gson gson) {
        JsonArray array = new JsonArray();
        for (Positionable gameObject : notMovable) {
            if (gameObject != null) {
                JsonObject object = new JsonObject();
                object.addProperty("type", gameObject.getClass().getSimpleName());
                object.addProperty("id", gameObject.getId());
                object.add("position", gson.toJsonTree(((Positionable) gameObject).getPosition()).getAsJsonObject());
                array.add(object);
            }
        }
        for (Positionable gameObject : gameObjects.values()) {
            if (gameObject != null) {
                JsonObject object = new JsonObject();
                object.addProperty("type", gameObject.getClass().getSimpleName());
                object.addProperty("id", gameObject.getId());
                object.add("position", gson.toJsonTree(((Positionable) gameObject).getPosition()).getAsJsonObject());
                array.add(object);
            }
        }

        return array;
    }

    public void start() {
        this.fieldInit();
        int id = EventHandler.getPlayerIdGenerator();
        for(Session key: playersOnline.keySet()) {
            Broker.getInstance().send("player_" + --id, Topic.POSSESS, playersOnline.get(key));
        }
        Gson gson = new Gson();
        Broker.getInstance().broadcast(Topic.REPLICA, allObjects(gson));

        ticker = new Ticker(this);
        ticker.loop();
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
        ArrayList<Integer> deadObjects = new ArrayList<>();
        for (Integer gameObject : gameObjects.keySet()) {
            Positionable object = gameObjects.get(gameObject);
            if (object instanceof Tickable) {
                ((Tickable) object).tick(elapsed);
            }
            if (object instanceof Temporary && ((Temporary) object).isDead()) {
                deadObjects.add(gameObject);
            }
            if (object instanceof Bomb && ((Temporary) object).isDead()) {
                int xSteady = object.getPosition().getX();
                int ySteady = object.getPosition().getY();
                int xLeft = object.getPosition().getX()-1;
                int xRight = object.getPosition().getX()+1;
                int yUp = object.getPosition().getY()+1;
                int yDown = object.getPosition().getY()-1;
                for(Integer objectDeadInd: gameObjects.keySet()) {
                    Positionable objectDead = gameObjects.get(objectDeadInd);
                    Point position = objectDead.getPosition();
                    if(objectDeadInd!=object.getId()  && ((position.getX() == xSteady && (position.getY() == yDown ||
                    position.getY() == yUp)) || (position.getY() == ySteady && (position.getX() == xLeft ||
                    position.getX() == xRight))) && objectDead instanceof Temporary) {
                        deadObjects.add(objectDeadInd);
                    }
                }
            }
        }
        for(Integer i: deadObjects) {
            gameObjects.remove(i);
        }
        while(!playersActions.isEmpty()) {
                Action action = playersActions.poll();
                Integer in = new Integer(action.getPlayer().substring(action.getPlayer().length() - 1));
                if(action.getType().equals(Action.Type.MOVE)) {
                    Pawn player = (Pawn) this.gameObjects.get(in);
                    player.move(action.getDirection());
                }
                if(action.getType().equals(Action.Type.PLANT)) {
                    this.addGameObject(new Bomb(this.getGameObjectId(),
                            gameObjects.get(in).getPosition(), 1,
                            ticker.getTickNumber()));
                }
        }
        sendReplika();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
