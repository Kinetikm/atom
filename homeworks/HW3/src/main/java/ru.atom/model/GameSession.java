package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.gameinterfaces.GameObject;
import ru.atom.gameinterfaces.Positionable;
import ru.atom.gameinterfaces.Temporary;
import ru.atom.gameinterfaces.Tickable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kinetik on 02.05.17.
 */

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private List<Positionable> gameObjects = new ArrayList<>();
    private int gameObjectId;

    public GameSession(int gameObjectId) {
        this.gameObjectId = gameObjectId;
    }

    public List<Positionable> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public int getGameObjectId() {
        return gameObjectId;
    }

    public void addGameObject(Positionable gameObject) {
        try {
            gameObjects.add(gameObject);
            log.info("Create an object " + gameObject.getClass() + " with id=" + gameObject.getId());
            this.gameObjectId += 1;
        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgumentException with " + gameObject.getClass() + ", id = " + gameObject.getId());
        } catch (Exception ex) {
            log.error("Exception " + ex.getClass() + " with cause" + ex.getCause() + " with sttrace "
                    + ex.getStackTrace());
        }
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
        ArrayList<Temporary> dead = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                ((Tickable) gameObject).tick(elapsed);
            }
            if (gameObject instanceof Temporary && ((Temporary) gameObject).isDead()) {
                dead.add((Temporary)gameObject);
            }
        }
        gameObjects.removeAll(dead);
    }
}